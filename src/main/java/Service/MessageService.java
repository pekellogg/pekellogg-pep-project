package Service;

import Model.Message;
import DAO.MessageDAO;
// import Service.AccountService;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for accountService when accountDAO is provided.
     * This is used for when a mock accountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * TO DO: Use the MessageDAO to add a new message to the database.
     *
     * This method should also return the added message. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* message Object given as the parameter will not contain the message's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted message,
     * which will contain the message's id. This way, any part of the application that uses this method has
     * all information about the new message, because knowing the new message's ID is necessary. This means that the
     * method should return the MEssage returned by the messageDAO's insertMessage method, and not the message provided by
     * the parameter 'message'.
     *
     * @param message an object representing a new Message.
     * @return the newly added message if the add operation was successful, including the message_id. We do this to
     *         inform our provide the front-end client with information about the added Message.
     */
    public Message addMessage(Message message) {
        // int messageAuthor = message.getPosted_by();
        // if (new AccountService().findById(messageAuthor).account_id == messageAuthor) {
        //     String messageText = message.getMessage_text();
        //     if (messageText.length() > 0  && messageText.length() <= 255) {
        //         return messageDAO.insertMessage(message);
        //     }   
        // }
        // return null;
        return messageDAO.insertMessage(message);
    }
}