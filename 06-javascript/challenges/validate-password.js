function validatePassword(password) {
    const errors = [];
    const suggestions = [];
    const common = ['password', '123456', 'qwerty', 'admin'];
    let score = 0;

    if (password.length < 8) {
        errors.push('Too short');
        suggestions.push('Use at least 8 characters');
    } else score += 20;

    if (!/[A-Z]/.test(password)) {
        errors.push('Missing uppercase');
        suggestions.push('Add uppercase letters');
    } else score += 15;

    if (!/[a-z]/.test(password)) {
        errors.push('Missing lowercase');
        suggestions.push('Add lowercase letters');
    } else score += 15;

    if (!/\d/.test(password)) {
        errors.push('Missing number');
        suggestions.push('Add numbers');
    } else score += 20;

    if (!/[!@#$%^&*()_+\-=]/.test(password)) {
        errors.push('Missing special character');
        suggestions.push('Add special characters');
    } else score += 20;

    if (common.includes(password.toLowerCase())) {
        errors.push('Common password');
        suggestions.push('Avoid common passwords');
        score = 10;
    }

    if (password.length > 12) score += 5;

    return {
        isValid: errors.length === 0,
        score: Math.min(score, 100),
        errors,
        suggestions
    };
}

console.log(validatePassword('abc'));
console.log(validatePassword('MyP@ssw0rd!2024'));
