const compose = (...fns) => value =>
    fns.reduceRight((acc, fn) => fn(acc), value);

const pipe = (...fns) => value =>
    fns.reduce((acc, fn) => fn(acc), value);

const addOne = x => x + 1;
const double = x => x * 2;
const square = x => x * x;

const composed = compose(addOne, double, square);
const piped = pipe(square, double, addOne);

console.log(composed(3));
console.log(piped(3));
