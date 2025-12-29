function customMap(arr, fn) {
    const result = [];
    for (let i = 0; i < arr.length; i++) {
        result[result.length] = fn(arr[i], i);
    }
    return result;
}

const mapped = customMap([1, 2, 3], n => n * 3);
console.log(mapped);
