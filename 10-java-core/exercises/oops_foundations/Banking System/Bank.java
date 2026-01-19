import java.util.ArrayList;
import java.util.List;

public class Bank {

    private String bankName;
    private List<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<Account>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccount(int accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }
        return null;
    }

    public double getTotalDeposits() {
        double total = 0;
        for (Account acc : accounts) {
            total = total + acc.getBalance();
        }
        return total;
    }

    public java.util.List<Account> getAllAccounts() {
        return accounts;
    }
}