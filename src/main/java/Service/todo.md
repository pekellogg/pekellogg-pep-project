<!-- You will need to design and create your own Service classes from scratch.
You should refer to prior mini-project lab examples and course material for guidance. -->


package Service;

import Model.Account;
<!-- import Model.Message; -->
import DAO.FlightDAO;
import java.util.List;

/**
 * Service class purpose:
 * 1. contain "business logic" and
 * 2. sit between web layer (controller) and persistence layer (DAO).
 * generally, perform tasks that aren't done via web or SQL--input is valid, add'l security checks, file logging, etc.
 */

## 1: Our API should be able to process new User registrations.

As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will not contain an account_id.

- The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful, the response status should be 400. (Client error)

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
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
     * TODO: Use the AccountDAO to add a new account to the database.
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
     * TODO: Use the AccountDAO to update an existing account from the database.
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
     * TODO: Use the AccountDAO to retrieve a List containing all accounts.
     * @return all accounts in the database.
     */
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    <!-- /**
     * TODO: Use the AccountDAO to retrieve a List containing all accounts departing from a certain city and arriving at
     * some other city. You could use the accountDAO.getAllAccountsFromCityToCity method.
     *
     * @param departure_city the departing city of the account.
     * @param arrival_city the arriving city of the account.
     * @return all accounts departing from departure_city and arriving at arrival_city.
     */
    public List<Account> getAllAccountsFromCityToCity(String departure_city, String arrival_city) {
        return accountDAO.getAllAccountsFromCityToCity(departure_city, arrival_city);
    } -->
}

