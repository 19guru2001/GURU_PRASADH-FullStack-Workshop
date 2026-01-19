import java.util.Optional;

public class User {

    private String name;
    private String middleName;
    private Address address;

    public User(String name, String middleName, Address address) {
        this.name = name;
        this.middleName = middleName;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getMiddleName() {
        return Optional.ofNullable(middleName);
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }
}
