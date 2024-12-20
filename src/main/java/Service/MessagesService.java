package Service;

import DAO.MessagesDAO;

public class MessagesService {
    private MessagesDAO accDAO;

    public MessagesService(){
        accDAO = new MessagesDAO();
    }
    
    public MessagesService(MessagesDAO aDAO){
        accDAO = aDAO;
    }
}
