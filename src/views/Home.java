/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.Access;
import controllers.BookController;
import controllers.SingletonManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import models.Admin;
import models.Book;
import models.enums.Category;
import models.enums.Genre;
import models.enums.SearchType;
import models.Person;


public class Home extends JFrame{
    JTextField frameTitle = new JTextField("Home");
    Access accessController = new Access();
    BookController bookController = new BookController();
    Person currentUser;

    public Home (){
        currentUser = SingletonManager.getInstance().getPerson();
        this.setLayout(new GridLayout(0,1,2,0));
        JMenuBar menuBar = new JMenuBar();
        if(SingletonManager.getInstance().getPerson()==null){
            JMenu menuLogin = new JMenu();
            menuLogin.setText("Login / Register");
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
            // Menu User Profile 

            // Menu Admin
            if(SingletonManager.getInstance().getPerson() instanceof Admin) {
                JMenu menuManageUser = new JMenu();
                menuManageUser.setText("Manage Users");
                menuManageUser.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        new ManageUser();
                        Home.this.dispose();
                    }
                });
                menuBar.add(menuManageUser);
                
                JMenu menuManageBook = new JMenu();
                menuManageBook.setText("Manage Books");
                menuManageBook.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        new ManageBook();
                        Home.this.dispose();
                    }
                });
                menuBar.add(menuManageBook);
            } else{
                JMenu menuProfile = new JMenu();
                menuProfile.setText("Profile");
                menuProfile.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new GUIUserProfile(currentUser);
                    Home.this.dispose();
                }
            });
            menuBar.add(menuProfile);
            }
        }
        JPanel booksSectionPanel = new JPanel();
        booksSectionPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2); // Adjust the insets to minimize the gap
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel searchSectionPanel = new JPanel();
        booksSectionPanel.add(searchSectionPanel);
        String[] cbArr = new String[Category.values().length+Genre.values().length+1];
        cbArr[0] = "Popular";
        int index = 1;
        for (Category categoryTmp : Category.values()) {
            cbArr[index++] = categoryTmp.name();
        }
        for (Genre genreTmp : Genre.values()) {
            cbArr[index++] = genreTmp.name();
        }
        JLabel searchLabel = new JLabel("Filter : ");
        JComboBox cbSearch =new JComboBox(cbArr); 
        
        searchSectionPanel.add(searchLabel);
        searchSectionPanel.add(cbSearch);
        
        JLabel labelBooks = new JLabel("Popular Now");  
        labelBooks.setFont(new java.awt.Font("Bookman Old Style", 1, 36));
        booksSectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gbc.gridy++;
        booksSectionPanel.add(labelBooks, gbc);

        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0,5,10,10));
        booksPanel.setBorder(new EmptyBorder(30, 10, 10, 10));
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
            booksPanel.add(imgIcon);
        }
        gbc.gridy++;
        booksSectionPanel.add(booksPanel, gbc);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchType stTmp = null;
                String search = null;

                for (Category cTmp : Category.values()) {
                    if(cbSearch.getSelectedItem().toString().equals(cTmp.name())) {
                        search = cTmp.name();
                        stTmp = SearchType.CATEGORY;
                    }
                }
                for (Genre gTmp : Genre.values()) {
                    if(cbSearch.getSelectedItem().toString().equals(gTmp.name())) {
                        search = gTmp.name();
                        stTmp = SearchType.GENRE;
                    }
                }
                if(stTmp!=null){                   
                    ArrayList<Book> booksResult = bookController.searchBook(stTmp, search, null);
                    labelBooks.setText("Search Result");
                    booksPanel.removeAll();
                    for(Book bookTmp : booksResult){
                        JLabel imgIcon = new JLabel(new ImageIcon(new ImageIcon(bookTmp.getPicPath()).getImage().getScaledInstance(200,300, Image.SCALE_DEFAULT)));
                        imgIcon.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")" + e);
                                new ShowBookDetail(bookController.searchBook(bookTmp.getIsbn()));
                            }
                        });
                        booksPanel.add(imgIcon);
                    }
                    booksPanel.revalidate();
                } else {
                    ArrayList<Book> popularBooks = bookController.getPopularBook();       
                    labelBooks.setText("Popular Now");
                    booksPanel.removeAll();
                    for(Book bookTmp : popularBooks){
                        JLabel imgIcon = new JLabel(new ImageIcon(new ImageIcon(bookTmp.getPicPath()).getImage().getScaledInstance(200,300, Image.SCALE_DEFAULT)));
                        imgIcon.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")" + e);
                                new ShowBookDetail(bookController.searchBook(bookTmp.getIsbn()));
                            }
                        });
                        booksPanel.add(imgIcon);
                    }
                    booksPanel.revalidate();
                }
            }
        });
        searchSectionPanel.add(searchBtn);
        JScrollPane scroll = new JScrollPane(booksSectionPanel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
        this.setJMenuBar(menuBar);
        this.setTitle(frameTitle.getText());
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
