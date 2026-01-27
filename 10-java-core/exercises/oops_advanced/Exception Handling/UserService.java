import java.util.HashSet;
import java.util.Set;

public class UserService {

    private static Set<String> emails = new HashSet<String>();

    public void register(User user) throws ValidationException {
        user.validate();

        if (emails.contains(user.getEmail())) {
            throw new DuplicateUserException("Email already registered");
        }

        try (DummyResource r = new DummyResource()) {
            emails.add(user.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) throws DatabaseException {
        try (DummyResource r = new DummyResource()) {
            if (emails.contains(email)) {
                return new User("Dummy", email, "Dummy123", 20);
            }
            return null;
        } catch (Exception e) {
            throw new DatabaseException("Database error");
        }
    }

    private static class DummyResource implements AutoCloseable {
        public void close() {
        }
    }
}