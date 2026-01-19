import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<String>();
        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(NotNull.class)) {
                    if (value == null) {
                        NotNull nn = field.getAnnotation(NotNull.class);
                        errors.add(nn.message());
                    }
                }

                if (field.isAnnotationPresent(Validate.class)) {
                    Validate v = field.getAnnotation(Validate.class);
                    if (value instanceof Integer) {
                        int num = (Integer) value;
                        if (num < v.min() || num > v.max()) {
                            errors.add(v.message());
                        }
                    }
                }

            } catch (IllegalAccessException e) {
            }
        }
        return errors;
    }
}
