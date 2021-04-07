package Results;

public class LoadResult {
    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    String message;
    boolean success;

    public boolean isSuccess() {
        return success;
    }
}
