const form = document.getElementById('signupForm');
const submitBtn = document.getElementById('submitBtn');

const fields = {
    user: {
        input: document.getElementById('user'),
        error: document.getElementById('userErr'),
        ok: document.getElementById('userOk'),
        valid: false
    },
    mail: {
        input: document.getElementById('mail'),
        error: document.getElementById('mailErr'),
        ok: document.getElementById('mailOk'),
        valid: false
    },
    pass: {
        input: document.getElementById('pass'),
        error: document.getElementById('passErr'),
        ok: document.getElementById('passOk'),
        valid: false
    },
    confirm: {
        input: document.getElementById('confirm'),
        error: document.getElementById('confirmErr'),
        ok: document.getElementById('confirmOk'),
        valid: false
    }
};

function enableButton() {
    const allValid = Object.values(fields).every(f => f.valid);
    submitBtn.disabled = !allValid;
    submitBtn.classList.toggle('active', allValid);
}

function markValid(field) {
    field.error.textContent = '';
    field.ok.textContent = '✔';
    field.valid = true;
    enableButton();
}

function markInvalid(field, msg) {
    field.error.textContent = msg;
    field.ok.textContent = '';
    field.valid = false;
    enableButton();
}

/* Username */
fields.user.input.addEventListener('blur', () => {
    const val = fields.user.input.value.trim();
    if (!/^[a-z0-9]{3,15}$/i.test(val)) {
        markInvalid(fields.user, '3–15 letters or numbers only');
    } else {
        markValid(fields.user);
    }
});

/* Email */
fields.mail.input.addEventListener('blur', () => {
    const val = fields.mail.input.value.trim();
    if (!/^\S+@\S+\.\S+$/.test(val)) {
        markInvalid(fields.mail, 'Invalid email format');
    } else {
        markValid(fields.mail);
    }
});

/* Password */
fields.pass.input.addEventListener('blur', () => {
    const val = fields.pass.input.value;
    if (!/^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/.test(val)) {
        markInvalid(fields.pass, 'Weak password');
    } else {
        markValid(fields.pass);
    }
});

/* Confirm Password */
fields.confirm.input.addEventListener('blur', () => {
    if (fields.confirm.input.value !== fields.pass.input.value) {
        markInvalid(fields.confirm, 'Passwords do not match');
    } else {
        markValid(fields.confirm);
    }
});

form.addEventListener('submit', (e) => {
    if (submitBtn.disabled) {
        e.preventDefault();
    } else {
        alert('Form submitted successfully!');
    }
});
