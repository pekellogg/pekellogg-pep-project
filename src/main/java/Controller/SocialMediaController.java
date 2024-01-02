package Controller;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

// TO DO: You will need to write your own endpoints and handlers for your controller
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        return app;
    }

    /**
     * Handler to post a new account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into a Register object.
     * If accountService returns a null account (meaning posting an account was unsuccessful, the API will return a 400
     * message (client error).
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    /**
     * Handler to post a login.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.find(account);
        if (foundAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(foundAccount));
        }
    }

    /**
     * Handler to post a message.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Account account = accountService.findById(message.getPosted_by());
        String messageText = message.getMessage_text();
        if (messageText.length() == 0  || messageText.length() > 255 || account == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(messageService.addMessage(message)));
        }
    }


    /**
     * Handler to retrieve all messages.
     * @param ctx the Javalin Context object handles information HTTP requests and generates responses. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.all());
    }

    /**
     * Handler to update an account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into a Account object.
     * to conform to RESTful standards, the account that is being updated is identified from the path parameter,
     * but the information required to update an account is retrieved from the request body.
     * If accountService returns a null account (meaning updating an account was unsuccessful), the API will return a 400
     * status (client error).
     *
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    // private void updateAccountHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account account = mapper.readValue(ctx.body(), Account.class);
    //     int account_id = Integer.parseInt(ctx.pathParam("account_id"));
    //     Account updatedAccount = accountService.updateAccount(account_id, account);
    //     // System.out.println(updatedAccount);
    //     if (updatedAccount == null) {
    //         ctx.status(400);
    //     } else {
    //         ctx.json(mapper.writeValueAsString(updatedAccount));
    //     }

    // }

}