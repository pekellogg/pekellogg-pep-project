package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
// import java.util.ArrayList;
import java.util.List;

// TO DO: You will need to write your own endpoints and handlers for your controller
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);
        return app;
    }

    /**
     * Handler to post a new account.
     * @param ctx Javalin Context object
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
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
     * @param ctx Javalin Context object
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
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
     * @param ctx Javalin Context object
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
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
     * @param ctx Javalin Context object
     */
    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.all());
    }

    /**
     * Handler to retrieve message by ID.
     * @param ctx Javalin Context object
     */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.findById(message_id);
        if (message != null) {
            ObjectMapper mapper = new ObjectMapper();
            ctx.json(mapper.writeValueAsString(message));
        }
    }

    /**
     * Handler to delete message by ID.
     * @param ctx Javalin Context object
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.findById(message_id);
        if (message != null) {
            messageService.deleteById(message.getMessage_id());
            ObjectMapper mapper = new ObjectMapper();
            ctx.json(mapper.writeValueAsString(message));
        }
    }

    /**
     * Handler to update a message.
     * @param ctxJavalin Context object
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageUpdates = mapper.readValue(ctx.body(), Message.class);
        int length = messageUpdates.getMessage_text().length();
        if (length != 0 && length <= 255) {
            Message updatedMessage = messageService.update(
                Integer.parseInt(ctx.pathParam("message_id")),
                messageUpdates
            );
            if (updatedMessage == null) {
                ctx.status(400);
            } else {
                ctx.json(mapper.writeValueAsString(updatedMessage));
            }
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve an account's messages by account ID.
     * @param ctx Javalin Context object
     */
    private void getAccountMessagesHandler(Context ctx) throws JsonProcessingException {
        Account account = accountService.findById(Integer.parseInt(ctx.pathParam("account_id")));
        List<Message> accountMessages = messageService.allByAccountId(account.getAccount_id());
        if (account != null && accountMessages != null) {
            ctx.json(accountMessages);
        }
    }
}