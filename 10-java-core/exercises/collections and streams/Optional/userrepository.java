import java.util.*;

public class UserRepository {

    private Map<Long, User> users = new HashMap<>();

    public void addUser(Long id, User user) {
        users.put(id, user);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return users.values()
                    .stream()
                    .filter(u -> u.getName().equalsIgnoreCase(email))
                    .findFirst();
    }
}
