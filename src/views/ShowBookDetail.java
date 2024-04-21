/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.BookController;
import controllers.SingletonManager;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import models.Book;
import models.User;

/**
 *
 * @author Darren
 */
public class ShowBookDetail extends JFrame {
    JTextField fTitle = new JTextField("Book Detail");
    public ShowBookDetail(Book book) {      
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2); // Adjust the insets to minimize the gap
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel imgIcon = new JLabel(new ImageIcon(new ImageIcon(book.getPicPath()).getImage().getScaledInstance(200,300, Image.SCALE_DEFAULT)));
        JLabel isbnLabel = new JLabel("ISBN : " + book.getIsbn());
        JLabel yearLabel = new JLabel("Year : " + book.getYear());
        JLabel titleLabel = new JLabel("Title : " + book.getTitle());
        JLabel genreLabel = new JLabel("Genre : " + book.getGenre().toString());
        JLabel categoryLabel = new JLabel("Category : " + book.getCategory().toString());
        JLabel authorLabel = new JLabel("Author : " + book.getAuthor());  
        
        bookPanel.add(imgIcon, gbc);
        gbc.gridy++;
        bookPanel.add(isbnLabel, gbc);
        gbc.gridy++;
        bookPanel.add(yearLabel, gbc);
        gbc.gridy++;
        bookPanel.add(titleLabel, gbc);
        gbc.gridy++;
        bookPanel.add(genreLabel, gbc);
        gbc.gridy++;
        bookPanel.add(categoryLabel, gbc);
        gbc.gridy++;
        bookPanel.add(authorLabel, gbc);
        
        if(SingletonManager.getInstance().getPerson()!=null) {
            if(SingletonManager.getInstance().getPerson() instanceof User) {
                BookController bc = new BookController();
                User userSession = ((User)SingletonManager.getInstance().getPerson());
                if (bc.ableToBorrow(book, userSession)) {
                    JButton borrow = new JButton("Borrow");
                    borrow.addActionListener(e -> {
                        if (bc.borrowBook(book, userSession))  {
                            JOptionPane.showMessageDialog(null, "Berhasil Meminjam", "Success", JOptionPane.PLAIN_MESSAGE);
                            new ShowBookDetail(book);
                            ShowBookDetail.this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal Meminjam", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                    gbc.gridy++;
                    bookPanel.add(borrow, gbc);
                } else if (bc.ableToQueue(book, userSession)) {
                    JButton queue = new JButton("Queue");
                    queue.addActionListener(e -> {
                        if (bc.addBookQueue(book, userSession))  {
                            JOptionPane.showMessageDialog(null, "Berhasil Mengantri", "Success", JOptionPane.PLAIN_MESSAGE);
                            new ShowBookDetail(book);
                            ShowBookDetail.this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal Meminjam", "Error", JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                    gbc.gridy++;
                    bookPanel.add(queue, gbc);
                } else {
                    if (bc.checkIfBookCurrentlyBorrowed(book, userSession)) {
                        JButton returnBook = new JButton("Return");
                        returnBook.addActionListener(e -> {
                            if (bc.returnBook(bc.getBorrowedBook(book, userSession)))  {
                                JOptionPane.showMessageDialog(null, "Berhasil Mengembalikan Buku", "Success", JOptionPane.PLAIN_MESSAGE);
                                new ShowBookDetail(book);
                                ShowBookDetail.this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal Mengembalikan Buku", "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        });
                        gbc.gridy++;
                        bookPanel.add(returnBook, gbc);
                    } else {
                        JLabel status = new JLabel("Anda sedang mengantri untuk meminjam buku ini"); 
                        gbc.gridy++;
                        bookPanel.add(status, gbc);
                    }
                }
            }
        }
        
        JScrollPane scroll = new JScrollPane(bookPanel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scroll);
        this.setTitle(fTitle.getText());
        this.setSize(400, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
