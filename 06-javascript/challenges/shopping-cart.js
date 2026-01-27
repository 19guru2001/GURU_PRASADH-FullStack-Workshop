function createShoppingCart() {
    let items = {};
    let discount = 0;

    return {
        addItem(product) {
            if (items[product.id]) {
                items[product.id].quantity += product.quantity;
            } else {
                items[product.id] = { ...product };
            }
        },

        updateQuantity(id, qty) {
            if (items[id] && qty > 0) {
                items[id].quantity = qty;
            }
        },

        removeItem(id) {
            delete items[id];
        },

        getItems() {
            return Object.values(items);
        },

        getTotal() {
            const total = Object.values(items)
                .reduce((sum, item) => sum + item.price * item.quantity, 0);
            return +(total - (total * discount / 100)).toFixed(2);
        },

        getItemCount() {
            return Object.values(items)
                .reduce((count, item) => count + item.quantity, 0);
        },

        isEmpty() {
            return Object.keys(items).length === 0;
        },

        applyDiscount(code, percent) {
            if (code && percent > 0) {
                discount = percent;
            }
        },

        clear() {
            items = {};
            discount = 0;
        }
    };
}

const cart = createShoppingCart();

cart.addItem({ id: 1, name: 'Laptop', price: 999, quantity: 1 });
cart.addItem({ id: 2, name: 'Mouse', price: 29, quantity: 2 });
cart.addItem({ id: 1, name: 'Laptop', price: 999, quantity: 1 });

console.log(cart.getItems());

cart.updateQuantity(1, 3);
cart.removeItem(2);

console.log(cart.getTotal());
console.log(cart.getItemCount());
console.log(cart.isEmpty());

cart.applyDiscount('SAVE10', 10);
console.log(cart.getTotal());

cart.clear();
console.log(cart.isEmpty());

