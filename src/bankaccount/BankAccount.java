/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankaccount;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nelson
 */
public class BankAccount {
    JFrame frame;
    JPanel panel;
    JLabel title;
    JLabel firstname;
    JTextField txtFirstname;
    JLabel lastname;
    JTextField txtLastname;
    JLabel DOB;
    JTextField txtDOB;
    JLabel password;
    JPasswordField txtPassword;
    JLabel confirmPassword;
    JPasswordField txtconfirmPasword;
    JButton register;
    JLabel login;
    Color colorRegister = new Color(44, 62, 80);
    
    public BankAccount(){
       initComponents();
        
    }
    public void initComponents(){
        frame = new JFrame("CREATE ACCOUNT");
        frame.setLayout(null);
        
        panel = new JPanel();
        panel.setSize(800,600);
        panel.setLayout(null);
        frame.add(panel);
        
        title = new JLabel("CREATE ACCOUNT");
        title.setFont(new Font("Garamond", Font.BOLD, 33));
        panel.add(title);
        
        firstname = new JLabel("First Name: ");
        firstname.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(firstname);
        
        txtFirstname = new JTextField(30);
        txtFirstname.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(txtFirstname);
        
        lastname = new JLabel("Last Name: ");
        lastname.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(lastname);
        
        txtLastname = new JTextField(30);
        txtLastname.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(txtLastname);
        
        DOB = new JLabel("Date of Birth: ");
        DOB.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(DOB);
        
        txtDOB = new JTextField(30);
        txtDOB.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(txtDOB);
        
        password = new JLabel("Password: ");
        password.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(password);
        
        txtPassword = new JPasswordField(30);
        txtPassword.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(txtPassword);
        
        confirmPassword = new JLabel("Confirm: ");
        confirmPassword.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(confirmPassword);
        
        txtconfirmPasword = new JPasswordField(30);
        txtconfirmPasword.setFont(new Font("Garamond", Font.BOLD, 20));
        panel.add(txtconfirmPasword);
        
        register = new JButton("Create Account");
        register.setBorder(null);
        register.setBackground(colorRegister);
        register.setForeground(Color.WHITE);
        register.setFont(new Font("Garamond", Font.BOLD, 25));
        panel.add(register);
        
         login = new JLabel("Have an account? Login!!");
        login.setFont(new Font("Garamond", Font.BOLD, 28));
        panel.add(login);
        
        title.setBounds(240,30,400,50);
        firstname.setBounds(180,100,200,50);
        txtFirstname.setBounds(300,110,300,30);
        lastname.setBounds(180,170,200,50);
        txtLastname.setBounds(300,180,300,30);
        DOB.setBounds(180, 240, 200, 50);
        txtDOB.setBounds(300,250,300,30);
        password.setBounds(180, 310, 200, 50);
        txtPassword.setBounds(300,320,300,30);
        confirmPassword.setBounds(180, 380, 200, 50);
        txtconfirmPasword.setBounds(300, 390, 300 , 30);
        register.setBounds(350, 450, 200, 50);
        login.setBounds(290,530,400,50);
        
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                frame.dispose();
                frame.setVisible(false);
                ClientLogin clientLogin = new ClientLogin();
            }
            
        });
        
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {                
                String firstname = txtFirstname.getText();
                String lastname = txtLastname.getText();
                String DOB = txtDOB.getText();
                int intBalance = 0;
                String balance = String.valueOf(intBalance);
                char[] pass = txtPassword.getPassword();
                String password = String.copyValueOf(pass);
                char[] confirmPass = txtPassword.getPassword();
                String confirmPassword = String.copyValueOf(confirmPass);
               
                
                if (register(firstname, lastname, DOB, balance, password)) {
                    JOptionPane.showMessageDialog(frame, "Succesful");
                }
//                if (validateFields(firstname, lastname, DOB, password, confirmPassword)) {
//                    System.out.println("Validation Succesful");
//                    register(firstname, lastname, DOB, balance, password);
//                } 
               
            }
        });
        
        frame.setSize(800,600);
        frame.setLocation(300, 100);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    
    }
    
    public boolean validateFields(String firstname, String lastname, String DOB, String password, String confirmPassword){
        boolean flag = false;
        if (firstname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill field Firstname");
            } else if (lastname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill field Lastname");
            } else if (DOB.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill field Date of Birth");
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill field password");
            } else if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please Fill field Confirm password");
            } else if(!password.equals(confirmPassword)){
                JOptionPane.showMessageDialog(null, "Confirm password must match password");
            }else{
                flag = true;
                System.out.println("All fields filled");
            }
        return flag;
     }
    
    public boolean register(String firstname,String lastname,String DOB, String balance,String password){
       
            
        try {
            
            Connection conn;
            PreparedStatement stmt;
            ResultSet rs;            
            conn = ConnectionManager.getConnection();
            System.out.println("Connection Succesful");
            stmt = conn.prepareStatement("INSERT into account(firstname,lastname,dob,balance,password) values(?,?,?,?,?);");
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, DOB);
            stmt.setString(4, balance);
            stmt.setString(5, password);
            System.out.println(stmt);
            stmt.execute();
//           
           return true;
        } catch (SQLException ex) {
            Logger.getLogger(BankAccount.class.getName()).log(Level.SEVERE, null, ex);            
        }
         return false;
    }
                
            
        
    
   
    public static void main(String[] args) {
        // TODO code application logic here
        new BankAccount();
    }
    
}
