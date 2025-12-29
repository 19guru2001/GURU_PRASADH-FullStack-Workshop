const typeOf = (value) => {
    if (Number.isNaN(value)) return 'nan';
    if (value === null) return 'null';

    const rawType = Object.prototype.toString.call(value)
        .slice(8, -1)
        .toLowerCase();

    if (rawType === 'number') return 'number';
    if (rawType === 'string') return 'string';
    if (rawType === 'boolean') return 'boolean';
    if (rawType === 'symbol') return 'symbol';
    if (rawType === 'array') return 'array';
    if (rawType === 'function') return 'function';
    if (rawType === 'date') return 'date';
    if (rawType === 'map') return 'map';
    if (rawType === 'set') return 'set';
    if (rawType === 'regexp') return 'regexp';
    if (rawType === 'error') return 'error';
    if (rawType === 'promise') return 'promise';
    if (rawType === 'object') return 'object';

    return rawType;
};

console.log(typeOf(null));
console.log(typeOf(undefined));
console.log(typeOf(42));
console.log(typeOf(NaN));
console.log(typeOf('hello'));
console.log(typeOf(true));
console.log(typeOf(Symbol()));
console.log(typeOf([]));
console.log(typeOf({}));
console.log(typeOf(() => {}));
console.log(typeOf(new Date()));
console.log(typeOf(new Map()));
console.log(typeOf(new Set()));
console.log(typeOf(/regex/));
console.log(typeOf(new Error()));
console.log(typeOf(Promise.resolve()));
