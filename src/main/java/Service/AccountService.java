package Service;

import Model.Account;
// import Model.Message;
import DAO.AccountDAO;
// import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for accountService when accountDAO is provided.
     * This is used for when a mock accountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * TO DO: Use the AccountDAO to add a new account to the database.
     *
     * This method should also return the added account. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* account Object given as the parameter will not contain the account's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted account,
     * which will contain the accounts's id. This way, any part of the application that uses this method has
     * all information about the new account, because knowing the new account's ID is necessary. This means that the
     * method should return the Account returned by the accountDAO's insertAccount method, and not the account provided by
     * the parameter 'account'.
     *
     * @param account an object representing a new Account.
     * @return the newly added account if the add operation was successful, including the account_id. We do this to
     *         inform our provide the front-end client with information about the added Account.
     */
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    /**
     * TO DO: Use the AccountDAO to update an existing account from the database.
     * You should first check that the account ID already exists. To do this, you could use an if statement that checks
     * if accountDAO.getAccountById returns null for the account's ID, as this would indicate that the account id does not
     * exist.
     *
     * @param account_id the ID of the account to be modified.
     * @param account an object containing all data that should replace the values contained by the existing account_id.
     *         the account object does not contain an accound ID.
     * @return the newly updated account if the update operation was successful. Return null if the update operation was
     *         unsuccessful. We do this to inform our application about successful/unsuccessful operations. (eg, the
     *         user should have some insight if they attempted to edit a nonexistent account.)
     */
    public Account updateAccount(int account_id, Account account) {
        if (accountDAO.getAccountById(account_id) == null) {
            accountDAO.insertAccount(account);
        } else {
            accountDAO.updateAccount(account_id, account);
        }
        return accountDAO.getAccountById(account_id);
    }

    /**
     * TO DO: Use the AccountDAO to retrieve a List containing all accounts.
     * @return all accounts in the database.
     */
    // public List<Account> getAllAccounts() {
    //     return accountDAO.getAllAccounts();
    // }

}

