package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * 
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * Use the messageDAO to persist a message to the database.
     * @param message
     * @return The message if it was successfully persisted, null otherwise
     */
    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    /**
     * Use the messageDAO to retrieve all messages from the database.
     * @return All messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Use the messageDAO to retrieve a message, identified by its id
     * @param id
     * @return A message indentified by id
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * Use the messageDAO to check whether a message exists in the database
     * @param id
     * @return True if the message exists, false otherwise
     */
    public boolean doesMessageExist(int id) {
        return messageDAO.getMessageById(id) != null;
    }

    /**
     * Use the messageDAO to delete a message from the database
     * @param id
     * @return The message deleted, or null if failed
     */
    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    /**
     * Use the messageDAO to update a message with new text in the database
     * @param id
     * @param text
     * @return The message updated, or null if failed
     */
    public Message updateMessage(int id, String text) {
        return messageDAO.updateMessage(id, text);
    }

    /**
     * Use the messageDAO to get all messages for an account in the database
     * @param id
     * @return The list of messages for the account
     */
    public List<Message> getAllMessagesByUser(int id) {
        return messageDAO.getAllMessagesByUser(id);
    }
}
