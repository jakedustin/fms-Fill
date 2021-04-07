package Services;

import DAOs.*;
import Models.Event;
import Models.Person;
import Models.User;
import Results.FillResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

public class FillService {
    public FillResult service(String username, int generations) {
        // PART 1: Verifying the username
        User user;
        try {
            user = verifyUsername(username);
        } catch (DataAccessException e) {
            return new FillResult(e.getMessage(), false);
        }

        // PART 2: Deleting the user's data
        try {
            deleteData(user.getUsername());
        } catch (DataAccessException e) {
            return new FillResult(e.getMessage(), false);
        }

        // PART 3: Filling the family tree
        generateTree(new Person(user.getPersonID(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                null,
                null,
                null), generations);

        try {
            addObjectsToDatabase();
            return new FillResult(getMessage(), true);
        } catch (DataAccessException e) {
            return new FillResult(e.getMessage(), false);
        }
    }

    private User verifyUsername(String username) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.find(username);
        db.closeConnection(true);
        return user;
    }

    private void deleteData(String username) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDAO personDAO = new PersonDAO(conn);
        personDAO.deleteMultiple(username);
        EventDAO eventDAO = new EventDAO(conn);
        eventDAO.deleteMultiple(username);
        db.closeConnection(true);
    }

    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    private void generateTree(Person child, int numGenerations) {
        FT ft = new FT(child,  numGenerations);
        ft.root.birthDate = new Random(System.currentTimeMillis()).nextInt(8) + 1994;
        events.add(new Event(ft.root.person, "Birth", ft.root.birthDate));
        ft.recursion(ft.root);
    }

    class FT {
        public FT(Person user, int numGenerations) {
            this.root = new Node(0, user);
            this.numGenerations = numGenerations;
        }

        Node root;
        int numGenerations;
        public Node recursion(Node currentNode) {
            people.add(currentNode.person);
            if (currentNode.level < numGenerations) {

                // generates parents
                Person father = new Person(currentNode.person.getAssociatedUsername(), "m");
                Node fatherNode = new Node(currentNode.level + 1, father);
                fatherNode.birthDate = generateBirthEvent(father, currentNode.birthDate).getYear();
                Person mother = new Person(currentNode.person.getAssociatedUsername(), "f");
                Node motherNode = new Node(currentNode.level + 1, mother);
                motherNode.birthDate = generateBirthEvent(mother, currentNode.birthDate).getYear();
                currentNode.left = recursion(fatherNode);
                currentNode.right = recursion(motherNode);

                // sets spouse ID of parents
                currentNode.left.person.setSpouseID(currentNode.right.person.getPersonID());
                currentNode.right.person.setSpouseID(currentNode.left.person.getPersonID());
                currentNode.left.marriageDate = generateWeddingEvent(currentNode.left.person, currentNode.right.person, currentNode.left.birthDate, currentNode.right.birthDate).getYear();
                currentNode.right.marriageDate = currentNode.left.marriageDate;

                //  sets parent ID of child
                currentNode.person.setFatherID(currentNode.left.person.getPersonID());
                currentNode.person.setMotherID(currentNode.right.person.getPersonID());
                currentNode.left.deathDate = generateDeathEvent(currentNode.left.person, currentNode.left.birthDate, currentNode.birthDate, currentNode.left.marriageDate).getYear();
                currentNode.right.deathDate = generateDeathEvent(currentNode.right.person, currentNode.right.birthDate, currentNode.birthDate, currentNode.right.marriageDate).getYear();
            }
            return currentNode;
        }

        class Node {
            int level;
            Person person;
            public Node(int level, Person person) {
                this.level = level;
                this.person = person;
            }
            Node left;
            Node right;

            //childBirthDate - 50 < birthDate < childBirthDate - 13
            int birthDate;

            // marriageDate < childBirthDate
            int marriageDate;

            // birthDate - 120 < deathDate
            int deathDate;
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    private Event generateBirthEvent(Person person, int childBirthYear) {
        int upperBound = childBirthYear - 13;
        int lowerBound = childBirthYear - 50;
        int randInt = new Random().nextInt(upperBound - lowerBound);
        Event birthEvent = new Event(person, "Birth", lowerBound + randInt);
        events.add(birthEvent);

        return birthEvent;
    }

    private Event generateWeddingEvent(Person person, Person spouse, int personBirthYear, int spouseBirthYear) {
        //both spouses should be married after age 18
        //spouses ages should be within five years of one another (there can be outliers sure but frankly idc
         int lowerBound;
         if (personBirthYear < spouseBirthYear) {
             lowerBound = spouseBirthYear + 20;
         }
         else {
             lowerBound = personBirthYear + 20;
         }

         int randInt = new Random().nextInt(10);
         Event marriageEvent = new Event(person, "Marriage", lowerBound + randInt);
         events.add(marriageEvent);
         events.add(new Event(marriageEvent, spouse));
         return marriageEvent;
    }

    private Event generateDeathEvent(Person person, int birthDate, int childBirthDate, int marriageDate) {
        int lowerBound = 10;
        if (childBirthDate < marriageDate) {
            lowerBound += childBirthDate;
        }
        else {
            lowerBound += marriageDate;
        }

        int upperBound = birthDate + 120;

        int deathDate = lowerBound + (new Random().nextInt(upperBound - lowerBound));
        Event deathEvent = new Event(person, "Death", deathDate);
        events.add(deathEvent);
        return deathEvent;
    }

    private void addObjectsToDatabase() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDAO personDAO = new PersonDAO(conn);
        personDAO.insertMultiple(people);
        EventDAO eventDAO = new EventDAO(conn);
        eventDAO.insertMultiple(events);

        db.closeConnection(true);
    }

    private String getMessage() {
        return "Successfully added " + people.size() + " persons and " + events.size() + " events to database.";
    }
}
