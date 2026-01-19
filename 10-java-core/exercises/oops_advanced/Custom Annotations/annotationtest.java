import java.util.List;

public class AnnotationTest {

    public static void main(String[] args) {

        User user = new User();
        user.setAge(15);

        List<String> errors = Validator.validate(user);

        for (String error : errors) {
            System.out.println(error);
        }
    }
}
