public class ShapeTest {

    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 4, 3, 4, 5)
        };

        for (Shape s : shapes) {
            s.displayInfo();
        }
    }
}