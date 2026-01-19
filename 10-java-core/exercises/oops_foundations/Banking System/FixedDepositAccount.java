public class FixedDepositAccount extends Account {

    private static final double INTEREST_RATE = 0.07;

    public FixedDepositAccount(String holderName, double balance) {
        super(holderName, balance);
    }

    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    public void withdraw(double amount) {
        throw new RuntimeException("Withdrawals not allowed for Fixed Deposit Account");
    }
}