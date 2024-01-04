package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    public Message insertMessage(Message message);
    public List<Message> all();
    public Message findById(int id);
    public Message deleteById(int id);
    public void update(int id, Message message);
    public List<Message> allByAccountId(int account_id);
}