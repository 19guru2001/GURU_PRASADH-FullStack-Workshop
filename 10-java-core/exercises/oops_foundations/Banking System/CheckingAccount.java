public class CheckingAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 500;

    public CheckingAccount(String holderName, double balance) {
        super(holderName, balance);
    }

    public double calculateInterest() {
        return 0;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + OVERDRAFT_LIMIT) {
            balance = balance - amount;
        }
    }
}