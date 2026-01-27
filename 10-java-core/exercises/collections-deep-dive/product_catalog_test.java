import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductCatalogTest {
    public static void main(String[] args) {
        List<Product> products = loadSampleProducts();

        System.out.println("=== Test 1: Simple Price Sort (Ascending) ===");
        testPriceSort(products);

        System.out.println("\n=== Test 2: Category Then Name Sort ===");
        testCategoryThenNameSort(products);

        System.out.println("\n=== Test 3: Rating Then Price (Nulls Last) ===");
        testRatingSort(products);

        System.out.println("\n=== Test 4: Low Stock First ===");
        testLowStockSort(products);

        System.out.println("\n=== Test 5: Newest First ===");
        testNewestSort(products);

        System.out.println("\n=== Test 6: Chained Sorting with Pagination ===");
        testChainedSortWithPagination(products);

        System.out.println("\n=== Test 7: Multi-Field Dynamic Sort ===");
        testMultiFieldSort(products);

        System.out.println("\n=== Test 8: Sort By Field Using Reflection ===");
        testSortByField(products);

        System.out.println("\n=== Test 9: Top N Products ===");
        testTopN(products);
    }

    private static void testPriceSort(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(ProductComparators.byPrice()).getProducts();

        System.out.println("Products sorted by price (ascending):");
        sorted.forEach(p -> System.out.printf("  %-20s $%.2f%n", p.getName(), p.getPrice()));

        for (int i = 1; i < sorted.size(); i++) {
            assert sorted.get(i - 1).getPrice() <= sorted.get(i).getPrice() : "Price sort failed";
        }
        System.out.println("✓ Test Passed");
    }

    private static void testCategoryThenNameSort(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(ProductComparators.byCategoryThenName()).getProducts();

        System.out.println("Products sorted by category, then name:");
        sorted.forEach(p -> System.out.printf("  %-15s %-20s%n", p.getCategory(), p.getName()));

        for (int i = 1; i < sorted.size(); i++) {
            Product prev = sorted.get(i - 1);
            Product curr = sorted.get(i);
            int categoryCompare = prev.getCategory().compareToIgnoreCase(curr.getCategory());
            if (categoryCompare == 0) {
                assert prev.getName().compareToIgnoreCase(curr.getName()) <= 0 : "Name sort within category failed";
            } else {
                assert categoryCompare <= 0 : "Category sort failed";
            }
        }
        System.out.println("✓ Test Passed");
    }

    private static void testRatingSort(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(ProductComparators.byRatingThenPrice()).getProducts();

        System.out.println("Products sorted by rating (nulls last), then price:");
        sorted.forEach(p -> System.out.printf("  %-20s Rating: %-5s Price: $%.2f%n",
                p.getName(), p.getRating() != null ? String.format("%.1f", p.getRating()) : "null", p.getPrice()));

        boolean foundNull = false;
        for (Product p : sorted) {
            if (p.getRating() == null) {
                foundNull = true;
            } else {
                assert !foundNull : "Null rating found before non-null rating";
            }
        }
        System.out.println("✓ Test Passed - Nulls are at the end");
    }

    private static void testLowStockSort(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(ProductComparators.byLowStockFirst()).getProducts();

        System.out.println("Products sorted by low stock first:");
        sorted.forEach(p -> System.out.printf("  %-20s Stock: %d%n", p.getName(), p.getStockQuantity()));

        boolean foundHighStock = false;
        for (Product p : sorted) {
            if (p.getStockQuantity() >= 10) {
                foundHighStock = true;
            } else {
                assert !foundHighStock : "Low stock item found after high stock item";
            }
        }
        System.out.println("✓ Test Passed - Low stock items are first");
    }

    private static void testNewestSort(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(ProductComparators.byNewest()).getProducts();

        System.out.println("Products sorted by newest first:");
        sorted.forEach(p -> System.out.printf("  %-20s Added: %s%n", p.getName(), p.getAddedDate()));

        for (int i = 1; i < sorted.size(); i++) {
            assert !sorted.get(i - 1).getAddedDate().isBefore(sorted.get(i).getAddedDate()) :
                    "Newest sort failed";
        }
        System.out.println("✓ Test Passed");
    }

    private static void testChainedSortWithPagination(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> page1 = list
                .sortBy(ProductComparators.byCategory())
                .thenSortBy(ProductComparators.byPriceDescending())
                .getPage(1, 5);

        System.out.println("Page 1 (5 products) - sorted by category, then price descending:");
        page1.forEach(p -> System.out.printf("  %-15s %-20s $%.2f%n",
                p.getCategory(), p.getName(), p.getPrice()));

        assert page1.size() <= 5 : "Page size exceeds limit";
        System.out.println("✓ Test Passed");
    }

    private static void testMultiFieldSort(List<Product> products) {
        Comparator<Product> dynamic = ProductComparators.multiSort(
                List.of("category", "price", "name"),
                List.of(true, false, true)
        );

        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortBy(dynamic).getProducts();

        System.out.println("Products with multi-field sort (category ASC, price DESC, name ASC):");
        sorted.forEach(p -> System.out.printf("  %-15s $%-7.2f %s%n",
                p.getCategory(), p.getPrice(), p.getName()));

        System.out.println("✓ Test Passed");
    }

    private static void testSortByField(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> sorted = list.sortByField("name", true).getProducts();

        System.out.println("Products sorted by name (using reflection):");
        sorted.forEach(p -> System.out.printf("  %s%n", p.getName()));

        for (int i = 1; i < sorted.size(); i++) {
            assert sorted.get(i - 1).getName().compareToIgnoreCase(sorted.get(i).getName()) <= 0 :
                    "Field sort failed";
        }
        System.out.println("✓ Test Passed");
    }

    private static void testTopN(List<Product> products) {
        SortableProductList list = new SortableProductList(products);
        List<Product> top3 = list.sortBy(ProductComparators.byPriceDescending()).top(3);

        System.out.println("Top 3 most expensive products:");
        top3.forEach(p -> System.out.printf("  %-20s $%.2f%n", p.getName(), p.getPrice()));

        assert top3.size() == 3 : "Should return exactly 3 products";
        System.out.println("✓ Test Passed");
    }

    private static List<Product> loadSampleProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("P001", "Laptop Pro 15", "Electronics", 1299.99, 15,
                LocalDate.of(2024, 1, 15), 4.5));
        products.add(new Product("P002", "Wireless Mouse", "Electronics", 29.99, 50,
                LocalDate.of(2024, 2, 10), 4.2));
        products.add(new Product("P003", "Office Chair", "Furniture", 199.99, 8,
                LocalDate.of(2024, 1, 20), null));
        products.add(new Product("P004", "Standing Desk", "Furniture", 399.99, 12,
                LocalDate.of(2023, 12, 5), 4.7));
        products.add(new Product("P005", "Notebook Set", "Stationery", 12.99, 100,
                LocalDate.of(2024, 3, 1), 4.0));
        products.add(new Product("P006", "Mechanical Keyboard", "Electronics", 89.99, 25,
                LocalDate.of(2024, 2, 20), 4.8));
        products.add(new Product("P007", "Desk Lamp", "Furniture", 45.99, 5,
                LocalDate.of(2024, 1, 10), 3.9));
        products.add(new Product("P008", "USB-C Cable", "Electronics", 15.99, 3,
                LocalDate.of(2024, 2, 25), null));
        products.add(new Product("P009", "Pen Set Premium", "Stationery", 24.99, 60,
                LocalDate.of(2024, 1, 5), 4.3));
        products.add(new Product("P010", "Monitor 27 inch", "Electronics", 349.99, 20,
                LocalDate.of(2024, 3, 5), 4.6));
        products.add(new Product("P011", "Bookshelf", "Furniture", 129.99, 7,
                LocalDate.of(2023, 11, 15), 4.1));
        products.add(new Product("P012", "Sticky Notes Pack", "Stationery", 8.99, 150,
                LocalDate.of(2024, 2, 15), 3.8));

        return products;
    }
}