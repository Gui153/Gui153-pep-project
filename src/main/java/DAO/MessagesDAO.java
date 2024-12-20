package DAO;

import Model.*;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDAO {

    public Message createMessage( Message newMsg ){

        //create connection
        Connection con = ConnectionUtil.getConnection();
        //System.out.println("DAO received user"+newUser);
        try{
            // insert the message
            
            String sql = "insert into message ( posted_by, message_text, time_posted_epoch) values ( ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, newMsg.getPosted_by());
            stmt.setString(2, newMsg.getMessage_text());
            stmt.setLong(3, newMsg.getTime_posted_epoch());

            stmt.executeUpdate();
            // if no keys are generated after the insert, then it means that the user was not created or there is a user with that name already
            ResultSet pkey = stmt.getGeneratedKeys();
            //System.out.println("before if");
            if(pkey.next()){
                //System.out.println(pkey.getRow());
                return new Message(pkey.getInt("message_id"), newMsg.getPosted_by(), newMsg.getMessage_text(), newMsg.getTime_posted_epoch() );
            }
             

        }catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return null;
    }

    public ArrayList<Message> getAllMessages(){
        ArrayList<Message> messages = new ArrayList<Message>();
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "select * from message;";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                messages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
            

        }catch(SQLException e){
            System.out.println(e);
        }

        return messages;
    }
    
    
}
