/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccount;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nelson
 */
public class ConnectionManager {
    static String url = "jdbc:mysql://localhost:3306/bank_account";    
    static String driverName = "com.mysql.jdbc.Driver";   
    static String username = "root";   
    static String password = "";
    static Connection con;
    

    public static Connection getConnection() {
       
        try {
            Class.forName(driverName);            
            con =  DriverManager.getConnection(url, username, password);
            System.out.println("connected");
            
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."+ ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    
}

