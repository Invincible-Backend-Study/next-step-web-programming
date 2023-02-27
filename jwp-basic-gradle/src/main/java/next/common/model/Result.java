package next.common.model;

import lombok.Getter;

@Getter
public class Result {
    private final boolean status;
    private final String message;

    private Result(boolean status) {
        this(status, "");
    }

    private Result(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result ok() {
        return new Result(true);
    }

    public static Result fail(String message) {
        return new Result(false, message);
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", message=" + message + "]";
    }
}
