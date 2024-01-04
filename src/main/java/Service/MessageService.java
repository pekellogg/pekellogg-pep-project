package Service;

import Model.Message;
import DAO.MessageDAOImplementation;
import java.util.List;

public class MessageService {
    MessageDAOImplementation messageDAO;

    public MessageService() {
        messageDAO = new MessageDAOImplementation();
    }

    /**
     * @param messageDAO
     */
    public MessageService(MessageDAOImplementation messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * @param message an object representing a new Message.
     * @return the newly added message if the add operation was successful, including the message_id.
     */
    public Message addMessage(Message message) {
        return messageDAO.insertMessage(message);
    }

    /**
     * @return all messages in the database.
     */
    public List<Message> all() {
        return messageDAO.all();
    }

    /**
     * @return found message if successful. Otherwise returns null.
     */
    public Message findById(int id) {
        return messageDAO.findById(id);
    }

    /**
     * @return deleted message if successful. If already deleted or unsuccessful delete operation, returns null.
     */
    public Message deleteById(int id) {
        return messageDAO.deleteById(id);
    }

    /**
     * @param message_id the ID of the message to be modified.
     * @param message an object containing all data that should replace the values contained by the existing message_id.
     * @return an update message if the update operation was successful. Return null if the update operation was
     *         unsuccessful.
     */
    public Message update(int message_id, Message message) {
        messageDAO.update(message_id, message);
        return messageDAO.findById(message_id);
    }

    /**
     * @return all messages in the database.
     */
    public List<Message> allByAccountId(int account_id) {
        return messageDAO.allByAccountId(account_id);
    }
}
