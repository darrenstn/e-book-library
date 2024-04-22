/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.BookController;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import models.Admin;
import models.Book;

/**
 *
 * @author Darren
 */
public class ManageBook extends JFrame{
JTextField frameTitle = new JTextField("Manage Books");
    BookController bookController = new BookController();
    public ManageBook(){
        this.setLayout(new GridLayout(0,1,2,2));
        JPanel headerPanel = new JPanel();
        JLabel manageLabel = new JLabel("Manage Books");
        manageLabel.setFont(new java.awt.Font("Bookman Old Style", 1, 72));
        headerPanel.add(manageLabel);
        this.add(headerPanel);
        if ((SingletonManager.getInstance().getPerson() instanceof Admin)) {
            JPanel booksPanel = new JPanel();
            booksPanel.setLayout(new GridLayout(0,1,10,10));
            ArrayList<Book> books = bookController.getAllBooks();
            
            for(Book bookTmp : books) {
                JLabel bookLabel = new JLabel("ISBN : " + bookTmp.getIsbn() + "   Title : " + bookTmp.getTitle() + "   Author : " + bookTmp.getAuthor() + "   Picture Path : " + bookTmp.getPicPath());
                booksPanel.add(bookLabel);
            }
            JScrollPane scroll = new JScrollPane(booksPanel);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.add(scroll);
        }
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(e -> {
            new AddBook();
        });
        this.add(addBookBtn);
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
