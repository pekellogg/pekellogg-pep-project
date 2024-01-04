package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAOImplementation implements AccountDAO {
    /**
     * @param username an account's username
     * @param password an account's password
     */
    @Override
    public Account find(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param account an object modeling an Account. The account object does not contain an account ID.
     */
    @Override
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        if (account.getUsername().length() != 0 && account.getPassword().length() >= 4) {
            try {
                String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPassword());
                ps.executeUpdate();
                ResultSet pkeyResultSet = ps.getGeneratedKeys();
                if (pkeyResultSet.next()) {
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param id an account ID.
     */
    @Override
    public Account getAccountById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id an account ID.
     * @param account an account object.
     */
    @Override
    public void updateAccount(int id, Account account) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE account SET username = ?, password = ? WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
