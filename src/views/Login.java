/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.Access;
import controllers.SingletonManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Darren
 */
public class Login extends JFrame{
    JTextField fTitle = new JTextField("Login");
    Access accessController = new Access();
    String name;
    String password;
    public Login(){
        JPanel panelLogin = new JPanel();
        GridLayout gl = new GridLayout(0,1,2,2);
        panelLogin.setLayout(gl);
        
        JLabel labelName = new JLabel("Name : ");
        JTextField nameLogin = new JTextField("");
        JLabel labelPassword = new JLabel("Password : ");
        JTextField passwordLogin = new JTextField("");
        JButton login = new JButton("Login");
        
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameLogin.getText();
                password = passwordLogin.getText();
                accessController.login(name, password);
                if (SingletonManager.getInstance().getPerson()!=null) {
                    new Home();
                    Login.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Login", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        
        panelLogin.add(labelName);
        panelLogin.add(nameLogin);
        panelLogin.add(labelPassword);
        panelLogin.add(passwordLogin);
        panelLogin.add(login);
        
        this.add(panelLogin);
        this.setTitle(fTitle.getText());
        this.setSize(300, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}