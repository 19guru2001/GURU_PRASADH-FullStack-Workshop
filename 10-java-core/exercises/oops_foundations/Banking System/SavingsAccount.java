public class SavingsAccount extends Account {

    private static final double INTEREST_RATE = 0.04;
    private static final double MIN_BALANCE = 100;

    public SavingsAccount(String holderName, double balance) {
        super(holderName, balance);
    }

    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance - amount >= MIN_BALANCE) {
            balance = balance - amount;
        }
    }
}