package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;


    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * Method defines the structure of the Javalin Social Media API. Javalin methods will use handler methods
     * to manipulate the Context object, which is a special object provided by Javalin which contains information about
     * HTTP requests and can generate responses.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);

        return app;
    }

    /**
     * Handler to register a new account.
     * The Jackson ObjectMapper will automatically convert the JSON of the POST request into an Account object.
     * The account's uesrname must not be blank and must not exist in the account table and the password must be at least 4 characters long.
     * If AccountService returns a null author (meaning posting an Account was unsuccessful), the API will return a 400
     * @param context The context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        
        boolean isUsernameValid = account.getUsername().length() != 0;
        boolean isPasswordValid = account.getPassword().length() >= 4;
        boolean usernameExists = accountService.doesUsernameExist(account.getUsername());
        
        if (isUsernameValid && isPasswordValid && !usernameExists) {
            Account addedAccount = accountService.createAccount(account);
            if (addedAccount != null) {
                context.json(mapper.writeValueAsString(addedAccount));
                context.status(200);
            } else {
                context.status(400);
            }
        } else {
            context.status(400);
        }
    }

    /**
     * Handler to log into an account.
     * For now, just matches the username and password with an account in the account table,
     * if it exists.
     * @param context The context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        
        if (accountService.doesAccountExist(account)) {
            context.status(200);
        } else {
            context.status(401);
        }
    }


}