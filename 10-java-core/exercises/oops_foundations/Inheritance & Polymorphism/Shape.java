public abstract class Shape {

    abstract double area();
    abstract double perimeter();

    public void displayInfo() {
        System.out.println("Area: " + area());
        System.out.println("Perimeter: " + perimeter());
        System.out.println();
    }
}