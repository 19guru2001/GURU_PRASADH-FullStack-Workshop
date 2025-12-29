function createEventEmitter() {
    const events = {};

    const addListener = (event, listener, once = false) => {
        events[event] = events[event] || [];
        events[event].push({ listener, once });
        return () => {
            events[event] = events[event].filter(l => l.listener !== listener);
        };
    };

    return {
        on(event, listener) {
            return addListener(event, listener, false);
        },

        once(event, listener) {
            addListener(event, listener, true);
        },

        emit(event, data) {
            if (!events[event]) return;

            events[event].forEach(l => l.listener(data));
            events[event] = events[event].filter(l => !l.once);
        },

        off(event) {
            if (event) events[event] = [];
        }
    };
}

const emitter = createEventEmitter();

const unsubscribe = emitter.on('userLogin', (user) => {
    console.log(`${user.name} logged in`);
});

emitter.on('userLogin', (user) => {
    console.log(`Send welcome email to ${user.email}`);
});

emitter.once('appStart', () => {
    console.log('App started - this only runs once');
});

emitter.emit('userLogin', { name: 'John', email: 'john@example.com' });
emitter.emit('appStart');
emitter.emit('appStart');

unsubscribe();
emitter.emit('userLogin', { name: 'Jane', email: 'jane@example.com' });

emitter.off('userLogin');
emitter.emit('userLogin', { name: 'Mark', email: 'mark@example.com' });
