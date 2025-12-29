<!DOCTYPE html>
<html>
<body>
<h1>JavaScript Objects</h1>
<h2>Object Destructuring</h2>

<p id="demo"></p>

<script>

const products = [
    { name: 'Blender', price: 30, rating: 4.2, category: 'Kitchen', inStock: true },
    { name: 'Headphones', price: 80, rating: 4.6, category: 'Electronics', inStock: true },
    { name: 'Bookshelf', price: 120, rating: 4.1, category: 'Furniture', inStock: true },
    { name: 'Laptop', price: 900, rating: 4.8, category: 'Electronics', inStock: false },
    { name: 'Desk', price: 200, rating: 4.3, category: 'Furniture', inStock: true },
    { name: 'Chair', price: 150, rating: 3.9, category: 'Furniture', inStock: false },
    { name: 'Smartphone', price: 700, rating: 4.7, category: 'Electronics', inStock: true },
    { name: 'Coffee Maker', price: 90, rating: 4.0, category: 'Kitchen', inStock: true },
    { name: 'Monitor', price: 300, rating: 4.5, category: 'Electronics', inStock: true },
];

function generateProductReport(products) {
    const totalProducts = products.length;
    const totalValue = products.reduce((sum, p) => sum + p.price, 0);
    const averagePrice = totalValue / totalProducts;
    const averageRating = products.reduce((sum, p) => sum + p.rating, 0) / totalProducts;

    const byCategory = {};
    for (const p of products) {
        if (!byCategory[p.category]) {
            byCategory[p.category] = { count: 0, totalValue: 0, products: [] };
        }
        byCategory[p.category].count++;
        byCategory[p.category].totalValue += p.price;
        byCategory[p.category].products.push(p);
    }

    const inStockCount = products.filter(p => p.inStock).length;
    const outOfStockCount = totalProducts - inStockCount;
    const inStockPercentage = (inStockCount / totalProducts) * 100;

    const priceRanges = {
        budget: products.filter(p => p.price < 100).length,
        midRange: products.filter(p => p.price >= 100 && p.price <= 500).length,
        premium: products.filter(p => p.price > 500).length,
    };

    const topRated = [...products].sort((a, b) => b.rating - a.rating).slice(0, 3);

    const recommendations = products.filter(p => p.rating >= 4.5 && p.inStock);

    return {
        totalProducts,
        totalValue,
        averagePrice,
        averageRating,
        byCategory,
        stockStatus: {
            inStock: inStockCount,
            outOfStock: outOfStockCount,
            inStockPercentage
        },
        priceRanges,
        topRated,
        recommendations
    };
}

console.log(generateProductReport(products));


</script>

</body>
</html>