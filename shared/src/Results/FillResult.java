package Results;

public class FillResult {
    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    private String message;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}
