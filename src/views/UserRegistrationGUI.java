package views;
import models.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.Profile;
import controllers.Register;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UserRegistrationGUI {

    private JTextField nameField, emailField, phoneField, bioField, passwordField;    
    private JButton registerButton;
    private JLabel statusLabel;
    private String pathFoto;
    private String nameUser;

    public UserRegistrationGUI() {
        JFrame frame = new JFrame("User Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        JLabel bioLabel = new JLabel("Bio:");
        bioField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel picPathLabel = new JLabel("Photo: ");
        JButton uploadFoto = new JButton("Upload Foto");
        JButton backToLogin = new JButton("Back to Login");
        String nameUser = nameField.getText();
        Profile profile = new Profile();

        uploadFoto.addActionListener(e -> {            
            pathFoto = profile.uploadFile(nameUser);
        });

        registerButton = new JButton("Register");
        statusLabel = new JLabel("");

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(bioLabel);
        frame.add(bioField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(picPathLabel);   
        frame.add(uploadFoto);
        frame.add(registerButton);
        frame.add(statusLabel);
        frame.add(backToLogin);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String bio = bioField.getText();
                String password = passwordField.getText();                

                User user = new User(0, password, name, email, phone, pathFoto, bio, 0);

                Register register = new Register();
                if (register.registerNewUser(user)) {
                    statusLabel.setText("User registered successfully!");
                    new Login();
                    frame.dispose();                    
                } else {
                    statusLabel.setText("Failed to register user!");
                }
            }
        });

        backToLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Login();
               frame.dispose();               
            }
        });

        frame.setVisible(true);
    }    

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserRegistrationGUI();
            }
        });
    }
}
