/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.AdminController;
import controllers.SingletonManager;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import models.Admin;
import models.Person;
import models.User;


public class ManageUser extends JFrame{
    JTextField frameTitle = new JTextField("Manage Users");
    AdminController adminController = new AdminController();
    public ManageUser(){
        this.setLayout(new GridLayout(0,1,2,2));
        JPanel headerPanel = new JPanel();
        JLabel manageLabel = new JLabel("Manage Users");
        manageLabel.setFont(new java.awt.Font("Bookman Old Style", 1, 72));
        headerPanel.add(manageLabel);
        this.add(headerPanel);
        if ((SingletonManager.getInstance().getPerson() instanceof Admin)) {
            JPanel usersPanel = new JPanel();
            usersPanel.setLayout(new GridLayout(0,3,10,10));
            ArrayList<Person> users = adminController.getAllUser();
            
            for(Person personTmp : users) {
                User userTmp = (User)personTmp;
                JLabel userLabel = new JLabel("Name : " + personTmp.getName() + ", Bio : " + userTmp.getBio() + ", Warning : " + userTmp.getWarning());
                JButton increaseBtn = new JButton("Warning++");
                increaseBtn.addActionListener(e -> {
                    adminController.increaseWarning(userTmp.getId());
                    new ManageUser();
                    this.dispose();
                });
                JButton decreaseBtn = new JButton("Warning--");
                decreaseBtn.addActionListener(e -> {
                    adminController.decreaseWarning(userTmp.getId());
                    new ManageUser();
                    this.dispose();
                });
                usersPanel.add(userLabel);
                usersPanel.add(increaseBtn);
                usersPanel.add(decreaseBtn);
            }
            JScrollPane scroll = new JScrollPane(usersPanel);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.add(scroll);
        }
        JButton homeBtn = new JButton("Back to Home");
        homeBtn.addActionListener(e -> {
            new Home();
            this.dispose();
        });
        this.add(homeBtn);
        this.setTitle(frameTitle.getText());
        this.setSize(800, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
