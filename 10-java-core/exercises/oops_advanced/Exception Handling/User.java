import java.util.regex.Pattern;

public class User {

    private String name;
    private String email;
    private String password;
    private int age;

    public User(String name, String email, String password, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public void validate() throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (name == null || name.length() < 2 || name.length() > 50) {
            errors.append("Name too short, ");
        }

        if (email == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            errors.append("Invalid email, ");
        }

        if (password == null || !Pattern.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$", password)) {
            errors.append("Password too weak, ");
        }

        if (age < 13) {
            errors.append("Must be 13+, ");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.substring(0, errors.length() - 2));
        }
    }

    public String getEmail() {
        return email;
    }
}