const taskInput = document.getElementById('taskInput');
const categorySelect = document.getElementById('categorySelect');
const addBtn = document.getElementById('addBtn');
const list = document.getElementById('taskList');
const countBox = document.getElementById('taskCount');
const filterButtons = document.querySelectorAll('[data-filter]');

let tasks = JSON.parse(localStorage.getItem('todos')) || [];
let activeFilter = 'All';

function save() {
    localStorage.setItem('todos', JSON.stringify(tasks));
}

function render() {
    list.innerHTML = '';
    let filtered = tasks.filter(t => activeFilter === 'All' || t.category === activeFilter);

    filtered.forEach(task => {
        const li = document.createElement('li');
        li.className = task.done ? 'done' : '';

        const left = document.createElement('span');
        const check = document.createElement('input');
        check.type = 'checkbox';
        check.checked = task.done;

        check.onchange = () => {
            task.done = check.checked;
            save();
            render();
        };

        left.append(check, ' ', task.text, ` [${task.category}]`);

        const del = document.createElement('span');
        del.textContent = 'X';
        del.className = 'remove';
        del.onclick = () => {
            tasks = tasks.filter(t => t.id !== task.id);
            save();
            render();
        };

        li.append(left, del);
        list.appendChild(li);
    });

    updateCount();
}

function updateCount() {
    const work = tasks.filter(t => t.category === 'Work').length;
    const personal = tasks.filter(t => t.category === 'Personal').length;
    countBox.textContent = `Work: ${work} | Personal: ${personal}`;
}

addBtn.onclick = () => {
    const text = taskInput.value.trim();
    if (!text) return;

    tasks.push({
        id: Date.now(),
        text,
        category: categorySelect.value,
        done: false
    });

    taskInput.value = '';
    save();
    render();
};

filterButtons.forEach(btn => {
    btn.onclick = () => {
        activeFilter = btn.dataset.filter;
        render();
    };
});

render();
