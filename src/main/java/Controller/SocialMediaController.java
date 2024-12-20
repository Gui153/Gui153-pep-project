package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

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


}