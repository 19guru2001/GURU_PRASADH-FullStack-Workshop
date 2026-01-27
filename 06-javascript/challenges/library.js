function createLibrary() {
    const books = {};
    const members = {};
    const borrowLog = [];

    const today = () => new Date();

    return {
        addBook(book) {
            if (books[book.isbn]) {
                books[book.isbn].copies += book.copies;
            } else {
                books[book.isbn] = { ...book };
            }
        },

        addMember(member) {
            members[member.id] = { ...member, history: [] };
        },

        borrowBook(memberId, isbn) {
            if (!members[memberId] || !books[isbn] || books[isbn].copies === 0) return;

            books[isbn].copies--;

            const record = {
                memberId,
                isbn,
                title: books[isbn].title,
                borrowedAt: today(),
                returnedAt: null
            };

            members[memberId].history.push(record);
            borrowLog.push(record);
        },

        returnBook(memberId, isbn) {
            const history = members[memberId].history;
            const record = history.find(
                r => r.isbn === isbn && r.returnedAt === null
            );

            if (record) {
                record.returnedAt = today();
                books[isbn].copies++;
            }
        },

        getAvailableCopies(isbn) {
            return books[isbn] ? books[isbn].copies : 0;
        },

        getMemberHistory(memberId) {
            return members[memberId]?.history || [];
        },

        getOverdueBooks() {
            const now = today();
            const limit = 14 * 24 * 60 * 60 * 1000;

            return borrowLog.filter(r =>
                !r.returnedAt && (now - r.borrowedAt) > limit
            );
        },

        searchBooks(keyword) {
            const term = keyword.toLowerCase();
            return Object.values(books).filter(
                b => b.title.toLowerCase().includes(term) ||
                     b.author.toLowerCase().includes(term)
            );
        }
    };
}

const library = createLibrary();

library.addBook({ isbn: '123', title: '1984', author: 'Orwell', copies: 3 });
library.addBook({ isbn: '456', title: 'Dune', author: 'Herbert', copies: 2 });

library.addMember({ id: 'M1', name: 'John', email: 'john@example.com' });
library.addMember({ id: 'M2', name: 'Jane', email: 'jane@example.com' });

library.borrowBook('M1', '123');
library.borrowBook('M2', '123');

console.log(library.getAvailableCopies('123'));

library.returnBook('M1', '123');

console.log(library.getMemberHistory('M1'));
console.log(library.getOverdueBooks());
console.log(library.searchBooks('orwell'));
