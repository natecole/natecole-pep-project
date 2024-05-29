package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * 
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Checks the account table for an account with the specified username
     * 
     * @param username
     * @return True if the account with the username exists, false otherwise
     */
    public boolean doesUsernameExist(String username) {
        if (accountDAO.getAccountByUsername(username) != null)
            return true;
        else
            return false;
    }

    /**
     * Use the AccountDAO to persist an author. The given Account will not have an id provided.
     * 
     * @param account The account to be created
     * @return The account created if the creation was successful
     */
    public Account createAccount(Account account){
        return accountDAO.createAccount(account);
    }

    /**
     * Retrieves an account from the account table with the same credentials as the passed account
     * 
     * @param account
     * @return The account if there is an account with the same credentials, null otherwise;
     */
    public Account getAccountByCredentials(Account account) {
        return accountDAO.getAccount(account);
    }

    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }
}
