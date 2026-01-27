import java.util.*;

public class Inventory {

    private List<Product> products = new ArrayList<>();
    private Set<String> categories = new HashSet<>();
    private Map<String, Product> productMap = new HashMap<>();
    private Queue<Product> lowStockQueue = new LinkedList<>();

    public void addProduct(Product product) {
        products.add(product);
        categories.add(product.getCategory());
        productMap.put(product.getId(), product);
        if (product.getQuantity() < 10) {
            lowStockQueue.add(product);
        }
    }

    public void updateProduct(Product product) {
        Product existing = productMap.get(product.getId());
        if (existing != null) {
            existing.setName(product.getName());
            existing.setCategory(product.getCategory());
            existing.setPrice(product.getPrice());
            existing.setQuantity(product.getQuantity());
            categories.add(product.getCategory());
            if (product.getQuantity() < 10) {
                lowStockQueue.add(existing);
            }
        }
    }

    public void deleteProduct(String id) {
        Product product = productMap.remove(id);
        if (product != null) {
            products.remove(product);
        }
    }

    public Product findById(String id) {
        return productMap.get(id);
    }

    public List<Product> getByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equals(category)) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> getAllSortedByPrice() {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort(new Comparator<Product>() {
            public int compare(Product a, Product b) {
                return Double.compare(a.getPrice(), b.getPrice());
            }
        });
        return sorted;
    }

    public Queue<Product> getLowStockAlerts() {
        return new LinkedList<>(lowStockQueue);
    }
}
