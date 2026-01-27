public class User {

    @NotNull(message = "Name is required")
    private String name;

    @Validate(min = 18, max = 100, message = "Age must be 18-100")
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
