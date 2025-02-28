package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;

import Service.AccountService;
import Service.MessagesService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessagesService messagesService;

    public SocialMediaController(){
        this.messagesService = new MessagesService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this:: registerHandler);
        app.post("/login", this:: loginHandler);
        app.post("messages", this::createPostHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Account acc = mapper.readValue(context.body(), Account.class);
        //System.out.println("received acount:"+acc);
        Account createdAcc = accountService.registerAccount(acc);
        //System.out.println("Created acount2:"+createdAcc);
        if(createdAcc != null){
            context.json(mapper.writeValueAsString(createdAcc));
        }
        else{
            context.status(400);
        }

    }

    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Account acc = mapper.readValue(context.body(), Account.class);
        //System.out.println("login received acount:"+acc);
        Account loginAcc = accountService.loginAccount(acc);
        //System.out.println("login  acount:"+loginAcc);
        if(loginAcc != null){
            context.json(mapper.writeValueAsString(loginAcc));
        }
        else{
            context.status(401);
        }

    }

    private void createPostHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Message msg = mapper.readValue(context.body(), Message.class);
        System.out.println("msg received:"+msg);
        Message newMsg = messagesService.createMessage( msg);
        System.out.println("msg:"+newMsg);
        if(newMsg != null){
            context.json(mapper.writeValueAsString(newMsg));
        }
        else{
            context.status(400);
        }

    }
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Message> messages = messagesService.getAllMessages();

        context.json(mapper.writeValueAsString(messages)).status(200);
        
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String id = context.pathParam("message_id");
        
        Message msg = messagesService.getMessageById(id);
        //System.out.println(msg);
        if( msg == null){
            context.result();
            return;
        }
        context.json(mapper.writeValueAsString(msg)).status(200);
        
    }

    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String id = context.pathParam("message_id");
        
        Message msg = messagesService.deleteMessageById(id);
        System.out.println(msg);
        if( msg == null){
            context.result();
            return;
        }
        context.json(mapper.writeValueAsString(msg)).status(200);
        
    }

    private void patchMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String id = context.pathParam("message_id");
        Message msg = mapper.readValue(context.body(), Message.class);
        
        int intId ;
        try{
            intId =  Integer.parseInt(id);
        }catch ( NumberFormatException e){
            System.out.println(e);
            context.status(400);
            return;
        }   

        msg.setMessage_id(intId);


        msg = messagesService.patchMessageById(msg);
        System.out.println(msg);
        if( msg == null){
            context.status(400);
            return;
        }
        context.json(mapper.writeValueAsString(msg)).status(200);
        
    }


    

    private void getMessagesByUserIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String id = context.pathParam("account_id");
        
        ArrayList<Message> msgs = messagesService.getMessageByAccountId(id);
        System.out.println(msgs);
        /*if( msgs == null || msgs.size() == 0){
            context.result();
            return;
        }*/

        context.json(mapper.writeValueAsString(msgs)).status(200);
        
    }

}