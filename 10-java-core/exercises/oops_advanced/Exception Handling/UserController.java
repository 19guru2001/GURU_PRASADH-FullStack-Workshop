public class UserController {

    private UserService service = new UserService();

    public Result register(String name, String email, String password, int age) {
        try {
            User user = new User(name, email, password, age);
            service.register(user);
            return Result.success("User registered successfully");
        } catch (ValidationException e) {
            return Result.failure(e.getMessage().split(", "));
        } catch (DuplicateUserException e) {
            return Result.failure(e.getMessage());
        } catch (Exception e) {
            return Result.failure("Unexpected error");
        }
    }
}