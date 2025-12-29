function deepClone(value) {
    if (value === null || typeof value !== 'object') {
        return value;
    }

    if (value instanceof Date) {
        return new Date(value.getTime());
    }

    if (value instanceof Set) {
        const newSet = new Set();
        value.forEach(v => newSet.add(deepClone(v)));
        return newSet;
    }

    if (value instanceof Map) {
        const newMap = new Map();
        value.forEach((v, k) => newMap.set(deepClone(k), deepClone(v)));
        return newMap;
    }

    if (Array.isArray(value)) {
        return value.map(item => deepClone(item));
    }

    const result = {};
    Object.keys(value).forEach(key => {
        result[key] = deepClone(value[key]);
    });

    return result;
}

const original = {
    name: 'John',
    address: { city: 'New York', zip: '10001' },
    hobbies: ['reading', 'gaming'],
    metadata: { created: new Date(), tags: new Set(['user', 'admin']) }
};

const cloned = deepClone(original);

cloned.address.city = 'Boston';
cloned.hobbies.push('swimming');

console.log(original.address.city);
console.log(original.hobbies);
