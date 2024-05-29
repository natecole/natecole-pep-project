package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.nimbus.State;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    /**
     * Inserts a message into the message table
     * @param message
     * @return The message created if successful, null otherwise
     */
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, 
                            message.getPosted_by(), 
                            message.getMessage_text(), 
                            message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retreives all messages from the message table
     * @return All messages
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrieves a message from the message table, identified by its id
     * @param id
     * @return The message identified by id
     */
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a message from the message table given an id
     * 
     * @param id
     * @return The message deleted if successful, null otherwise
     */
    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
        try {
            connection.setAutoCommit(false);

            String selectSQL = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);

            selectStmt.setInt(1, id);

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                message = new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
            }

            //Write SQL logic here
            if(message != null) {
                String deleteSQL = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);

                deleteStmt.setInt(1, id);

                deleteStmt.executeUpdate();
                connection.commit();
                return message;
            }
        }catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a message in the message table given an id and replacement text
     * the text is assumed to be valid
     * 
     * @param id
     * @param text
     * @return The message with the new text if successful, null otherwise
     */
    public Message updateMessage(int id, String text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);

            //Write SQL logic here
            String updateSQL = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);

            updateStmt.setString(1, text);
            updateStmt.setInt(2, id);

            updateStmt.executeUpdate();
            connection.commit();

            String selectSQL = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);

            selectStmt.setInt(1, id);

            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return new Message(rs.getInt("message_id"), 
                            rs.getInt("posted_by"), 
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all messages for an account given the account's id.
     * The account id should match the message's "posted_by" attribute
     * @param id
     * @return A list of all messages posted by the account
     */
    public List<Message> getAllMessagesByUser(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
