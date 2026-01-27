import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortableProductList {
    private List<Product> products;
    private Comparator<Product> currentComparator;

    public SortableProductList(List<Product> products) {
        this.products = new ArrayList<>(products);
        this.currentComparator = null;
    }

    public SortableProductList sortBy(Comparator<Product> comparator) {
        this.currentComparator = comparator;
        this.products.sort(comparator);
        return this;
    }

    public SortableProductList thenSortBy(Comparator<Product> comparator) {
        if (this.currentComparator == null) {
            return sortBy(comparator);
        }
        this.currentComparator = this.currentComparator.thenComparing(comparator);
        this.products.sort(this.currentComparator);
        return this;
    }

    public SortableProductList sortByField(String fieldName, boolean ascending) {
        try {
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + 
                              fieldName.substring(1);
            Method getter = Product.class.getMethod(methodName);

            Comparator<Product> comparator = (p1, p2) -> {
                try {
                    Object v1 = getter.invoke(p1);
                    Object v2 = getter.invoke(p2);

                    if (v1 == null && v2 == null) return 0;
                    if (v1 == null) return 1;
                    if (v2 == null) return -1;

                    if (v1 instanceof Comparable) {
                        @SuppressWarnings("unchecked")
                        Comparable<Object> c1 = (Comparable<Object>) v1;
                        return c1.compareTo(v2);
                    }

                    return 0;
                } catch (Exception e) {
                    throw new RuntimeException("Error comparing field: " + fieldName, e);
                }
            };

            if (!ascending) {
                comparator = comparator.reversed();
            }

            return sortBy(comparator);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No getter found for field: " + fieldName, e);
        }
    }

    public List<Product> top(int n) {
        return products.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Product> getPage(int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and size must be positive");
        }

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, products.size());

        if (startIndex >= products.size()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(products.subList(startIndex, endIndex));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public int size() {
        return products.size();
    }
}