package Service;

import DAO.AccountDAO;
import Model.Account;
public class AccountService {
    private AccountDAO accDAO;

    public AccountService(){
        accDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO aDAO){
        accDAO = aDAO;
    }

    public Account registerAccount (Account newAcc){
        System.out.println("service received before if:"+newAcc);
        //check if it is a valid user
        
        if(newAcc.getPassword().length() < 4 || newAcc.getUsername().length() == 0){
            return null;
        }

        if(accDAO.getAccountByUsername(newAcc) != null){
            return null;
        }

        //System.out.println("service received:"+newAcc);
        Account created = accDAO.registerAccount(newAcc);
        //System.out.println("service created:"+created);

        return created;
    }

    public Account loginAccount (Account acc){
       // System.out.println("service received before if:"+acc);
        //check if it is a valid user

        
        //System.out.println("service login received:"+acc);
        Account logged = accDAO.loginAccount(acc);
        //System.out.println("service login logged in:"+logged);

        return logged;
    }

}
