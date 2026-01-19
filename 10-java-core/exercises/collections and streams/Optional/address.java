import java.util.Optional;

public class Address {

    private String street;
    private String apartment;

    public Address(String street, String apartment) {
        this.street = street;
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public Optional<String> getApartment() {
        return Optional.ofNullable(apartment);
    }
}
