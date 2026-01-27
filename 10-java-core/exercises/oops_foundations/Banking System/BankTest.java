public class BankTest {

    public static void main(String[] args) {

        Bank bank = new Bank("MyBank");

        Account savings = new SavingsAccount("Alice", 1000);
        Account checking = new CheckingAccount("Bob", 500);
        Account fd = new FixedDepositAccount("Charlie", 10000);

        bank.addAccount(savings);
        bank.addAccount(checking);
        bank.addAccount(fd);

        savings.deposit(500);
        checking.withdraw(800);

        System.out.println("Total deposits: $" + bank.getTotalDeposits());

        for (Account acc : bank.getAllAccounts()) {
            System.out.println(acc.getHolderName() + " interest: $" + acc.calculateInterest());
        }
    }
}