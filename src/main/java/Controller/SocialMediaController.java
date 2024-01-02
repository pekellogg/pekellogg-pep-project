package Controller;
import Model.Account;
// import Model.Message;
import Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

// TO DO: You will need to write your own endpoints and handlers for your controller
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController() {
        accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        return app;
    }

    /**
     * Handler to retrieve all accounts.
     * @param ctx the Javalin Context object handles information HTTP requests and generates responses. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    // private void getAllAccountsHandler(Context ctx){
    //     ctx.json(accountService.getAllAccounts());
    // }

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