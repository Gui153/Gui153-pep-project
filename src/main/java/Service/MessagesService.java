package Service;

import DAO.AccountDAO;
import DAO.MessagesDAO;
import Model.Message;
import Util.ConnectionUtil;

public class MessagesService {
    private MessagesDAO msgDAO;
    private AccountDAO accDAO;

    public MessagesService(){
        accDAO = new AccountDAO();
        msgDAO = new MessagesDAO();
    }
    
    public MessagesService(MessagesDAO msgDAO){
        this.msgDAO = msgDAO;
        accDAO = new AccountDAO();
    }


    public Message createMessage(Message newMsg){
        if(newMsg.getMessage_text().length() == 0 || newMsg.getMessage_text().length() >255){
            return null;
        }
        
        if(  accDAO.getAccountByID(newMsg.getPosted_by()) == null){
            return null;
        }

        return msgDAO.createMessage(newMsg);
    }
}
