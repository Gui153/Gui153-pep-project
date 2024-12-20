package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class AccountDAO {
    
    //This method registers a new user to the DB
    public Account registerAccount( Account newUser ){

        //create connection
        Connection con = ConnectionUtil.getConnection();
        //System.out.println("DAO received user"+newUser);
        try{
            // insert the user
            String sql = "insert into account ( username, password) values ( ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword());

            stmt.executeUpdate();
            // if no keys are generated after the insert, then it means that the user was not created or there is a user with that name already
            ResultSet pkey = stmt.getGeneratedKeys();
            //System.out.println("before if");
            if(pkey.next()){
                //System.out.println(pkey.getRow());
                return new Account(pkey.getInt(1), newUser.getUsername(), newUser.getPassword());
            }
             

        }catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return null;
    }
    
    public Account getAccountByUsername( Account user ){

        //create connection
        Connection con = ConnectionUtil.getConnection();
        System.out.println("DAO received user"+user);
        try{
            // insert the user
            String sql = "select * from account where username =  ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                System.out.println(rs.getRow());
                return new Account(rs.getInt(1), user.getUsername(), user.getPassword());
            }
             

        }catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return null;
    }

    public Account loginAccount( Account user ){

        //create connection
        Connection con = ConnectionUtil.getConnection();
        //System.out.println("DAO received user"+user);
        try{
            // insert the user
            String sql = "select * from account where username =  ? and password = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                //System.out.println(rs.getRow());
                return new Account(rs.getInt(1), user.getUsername(), user.getPassword());
            }
             

        }catch(SQLException e){
            System.out.println(e);
            return null;
        }

        return null;
    }


}
