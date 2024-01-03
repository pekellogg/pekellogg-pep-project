package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for messageService when messageDAO is provided.
     * This is used for when a mock messageDAO is used in test cases.
     * allows the testing of MessageService independently of MessageDAO.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * @param message an object representing a new Message.
     * @return the newly added message if the add operation was successful, including the message_id. We do this to
     *         inform our provide the front-end client with information about the added Message.
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

    public Message findById(int id) {
        return messageDAO.findById(id);
    }

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
}
