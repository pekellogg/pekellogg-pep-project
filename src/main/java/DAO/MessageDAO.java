package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

public class MessageDAO {
    /**
     * TO DO: Add a message record into the database which matches the values contained in the message object.
     * You can use the getters already written in the Message class to retrieve its values (getX(),
     * getY()). The message_id will be automatically generated by the SQL database, and JDBC will be able
     * to retrieve the generated ID automatically. 
     * Remember that the format of a insert PreparedStatement written as a string works something like this:
     * String sql = "insert into TableName (ColumnName1, ColumnName2) values (?, ?);";
     * The question marks will be filled in by the preparedStatement setString, setInt, etc methods. they follow
     * this format, where the first argument identifies the question mark to be filled (left to right, starting
     * from zero) and the second argument identifies the value to be used:
     * preparedStatement.setString(1,string1);
     * preparedStatement.setString(2,string2);
     *
     * @param account an object modeling an Account. The account object does not contain an account ID.
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        // String messageText = message.getMessage_text();
        // if (messageText.length() > 0  && messageText.length() <= 255) {
            try {
                String sql = "INSERT INTO message (message_text) values (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, message.getMessage_text());
                // preparedStatement.setString(2, account.getPassword());
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if (pkeyResultSet.next()) {
                    int generated_message_id = (int) pkeyResultSet.getLong(1);
                    return new Message(
                        generated_message_id,
                        message.getPosted_by(),
                        message.getMessage_text(),
                        message.getTime_posted_epoch()
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        // }
        return null;
    }
}
