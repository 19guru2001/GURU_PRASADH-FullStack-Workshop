import java.util.ArrayList;
import java.util.List;

public class Result {

    private boolean success;
    private String message;
    private List<String> errors = new ArrayList<String>();

    public static Result success(String message) {
        Result r = new Result();
        r.success = true;
        r.message = message;
        return r;
    }

    public static Result failure(String... msgs) {
        Result r = new Result();
        r.success = false;
        for (String m : msgs) {
            r.errors.add(m);
        }
        return r;
    }

    public String toString() {
        if (success) {
            return "Result{success=true, message=" + message + "}";
        }
        return "Result{success=false, errors=" + errors + "}";
    }
}