package Service;

import Model.Account;
import DAO.AccountDAOImplementation;

public class AccountService {
    AccountDAOImplementation accountDAO;

    public AccountService() {
        accountDAO = new AccountDAOImplementation();
    }

    /**
     * Constructor for accountService when accountDAO is provided.
     * This is used for when a mock accountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public AccountService(AccountDAOImplementation accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * @param account an object representing a new Account.
     * @return the newly added account if the add operation was successful, including the account_id. We do this to
     *         inform our provide the front-end client with information about the added Account.
     */
    public Account find(Account account) {
        return accountDAO.find(account);
    }

    public Account findById(int id) {
        return accountDAO.getAccountById(id);
    }

    /**
     * @param account an object representing a new Account.
     * @return the newly added account if the add operation was successful, including the account_id. We do this to
     *         inform our provide the front-end client with information about the added Account.
     */
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    /**
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

}