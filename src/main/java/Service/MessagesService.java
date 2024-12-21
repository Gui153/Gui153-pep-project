package Service;

import DAO.AccountDAO;
import DAO.MessagesDAO;
import Model.Message;
import Util.ConnectionUtil;
import java.util.ArrayList;

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
   
    public ArrayList<Message> getAllMessages(){
        

        return msgDAO.getAllMessages();
    }

    public Message getMessageById(String id){
        int intId ;
        try{
            intId =  Integer.parseInt(id);
        }catch ( NumberFormatException e){
            System.out.println(e);
            return null;
        }   
        return msgDAO.getMessageById(intId);
    }


    public Message deleteMessageById(String id){
        int intId ;
        try{
            intId =  Integer.parseInt(id);
        }catch ( NumberFormatException e){
            System.out.println(e);
            return null;
        }

        return msgDAO.deleteMessageById(intId);
    }


    public Message patchMessageById(Message msg){
          if(msg.getMessage_text().length() == 0 || msg.getMessage_text().length()  > 255){
            return null;
          }
          Message complete = getMessageById(msg.getMessage_id()+"");
          if(complete == null){
            return null;
          }

          complete.setMessage_text(msg.getMessage_text());

        return msgDAO.patchMessageById(complete);
    }
    
}
