public class RegistrationTest {

    public static void main(String[] args) {

        UserController controller = new UserController();

        Result result1 = controller.register("J", "invalid-email", "weak", 10);
        System.out.println(result1);

        Result result2 = controller.register("John", "existing@email.com", "Strong1Pass", 25);
        System.out.println(result2);

        Result result3 = controller.register("John", "existing@email.com", "Strong1Pass", 25);
        System.out.println(result3);

        Result result4 = controller.register("Alice", "alice@email.com", "Secure123", 30);
        System.out.println(result4);
    }
}