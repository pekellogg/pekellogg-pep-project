package DAO;

import Model.Account;

public interface AccountDAO {
    public Account find(Account account);
    public Account insertAccount(Account account);
    public Account getAccountById(int id);
    public void updateAccount(int id, Account account);
}
