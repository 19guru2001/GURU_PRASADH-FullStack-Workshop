import java.util.Comparator;
import java.util.List;

public class ProductComparators {

    public static Comparator<Product> byPrice() {
        return Comparator.comparingDouble(Product::getPrice);
    }

    public static Comparator<Product> byPriceDescending() {
        return Comparator.comparingDouble(Product::getPrice).reversed();
    }

    public static Comparator<Product> byName() {
        return Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byCategory() {
        return Comparator.comparing(Product::getCategory, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byCategoryThenName() {
        return Comparator.comparing(Product::getCategory, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byRatingThenPrice() {
        return Comparator.comparing(Product::getRating, 
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparingDouble(Product::getPrice);
    }

    public static Comparator<Product> byLowStockFirst() {
        return Comparator.comparing((Product p) -> p.getStockQuantity() < 10 ? 0 : 1)
                .thenComparing(Product::getStockQuantity)
                .thenComparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
    }

    public static Comparator<Product> byNewest() {
        return Comparator.comparing(Product::getAddedDate).reversed();
    }

    public static Comparator<Product> multiSort(List<String> fields, List<Boolean> ascending) {
        if (fields == null || fields.isEmpty() || fields.size() != ascending.size()) {
            throw new IllegalArgumentException("Fields and ascending lists must be non-empty and same size");
        }

        Comparator<Product> comparator = null;

        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i).toLowerCase();
            boolean asc = ascending.get(i);

            Comparator<Product> fieldComparator = getComparatorForField(field);
            if (!asc) {
                fieldComparator = fieldComparator.reversed();
            }

            if (comparator == null) {
                comparator = fieldComparator;
            } else {
                comparator = comparator.thenComparing(fieldComparator);
            }
        }

        return comparator;
    }

    private static Comparator<Product> getComparatorForField(String field) {
        return switch (field) {
            case "price" -> Comparator.comparingDouble(Product::getPrice);
            case "name" -> Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
            case "category" -> Comparator.comparing(Product::getCategory, String.CASE_INSENSITIVE_ORDER);
            case "stock", "stockquantity" -> Comparator.comparingInt(Product::getStockQuantity);
            case "rating" -> Comparator.comparing(Product::getRating,
                    Comparator.nullsLast(Comparator.naturalOrder()));
            case "date", "addeddate" -> Comparator.comparing(Product::getAddedDate);
            case "id" -> Comparator.comparing(Product::getId);
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }
}