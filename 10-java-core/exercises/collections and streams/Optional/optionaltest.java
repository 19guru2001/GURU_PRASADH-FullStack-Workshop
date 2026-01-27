
public class OptionalTest {

    public static void main(String[] args) {

        UserRepository repository = new UserRepository();

        Address address = new Address("Main St", "Apt 101");
        User user1 = new User("Alice", null, address);
        User user2 = new User("Bob", "M.", null);

        repository.addUser(1L, user1);
        repository.addUser(2L, user2);

        String apartment1 = repository.findById(1L)
            .flatMap(User::getAddress)
            .flatMap(Address::getApartment)
            .orElse("N/A");

        String apartment2 = repository.findById(2L)
            .flatMap(User::getAddress)
            .flatMap(Address::getApartment)
            .orElse("N/A");

        System.out.println("User 1 apartment: " + apartment1);
        System.out.println("User 2 apartment: " + apartment2);
    }
}
