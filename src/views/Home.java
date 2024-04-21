/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.Access;
import controllers.BookController;
import controllers.SingletonManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import models.Book;

/**
 *
 * @author Darren
 */
public class Home extends JFrame{
    JTextField frameTitle = new JTextField("Home");
    Access accessController = new Access();
    BookController bookController = new BookController();
    public Home (){
        this.setLayout(new GridLayout(0,1,2,0));
        JMenuBar menuBar = new JMenuBar();
        if(SingletonManager.getInstance().getPerson()==null){
            JMenu menuLogin = new JMenu();
            menuLogin.setText("Login");
            menuLogin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new Login();
                    Home.this.dispose();
                }
            });
            menuBar.add(menuLogin);
        } else {
            JMenu menuLogout = new JMenu();
            menuLogout.setText("Logout");
            menuLogout.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    accessController.logoff();
                    new Home();
                    Home.this.dispose();
                }
            });
            menuBar.add(menuLogout);
        }
        JPanel popularBooksSectionPanel = new JPanel();
        popularBooksSectionPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2); // Adjust the insets to minimize the gap
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel labelPopularBooks = new JLabel("Popular Now");  
        labelPopularBooks.setFont(new java.awt.Font("Bookman Old Style", 1, 36));
        popularBooksSectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        popularBooksSectionPanel.add(labelPopularBooks, gbc);

        JPanel popularBooksPanel = new JPanel();
        popularBooksPanel.setLayout(new GridLayout(0,5,10,10));
        popularBooksPanel.setBorder(new EmptyBorder(30, 10, 10, 10));
        ArrayList<Book> popularBooks = bookController.getPopularBook();
        
        for(Book bookTmp : popularBooks){
            JLabel imgIcon = new JLabel(new ImageIcon(new ImageIcon(bookTmp.getPicPath()).getImage().getScaledInstance(200,300, Image.SCALE_DEFAULT)));
            imgIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")" + e);
                    new ShowBookDetail(bookController.searchBook(bookTmp.getIsbn()));
                }
            });
            popularBooksPanel.add(imgIcon);
        }
        gbc.gridy++;
        popularBooksSectionPanel.add(popularBooksPanel, gbc);
        
        this.add(popularBooksSectionPanel);
        this.setJMenuBar(menuBar);
        this.setTitle(frameTitle.getText());
        this.setSize(1100, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
