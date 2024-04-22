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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import models.Book;
import models.Review;
import models.User;

public class ShowBookDetail extends JFrame {
    
    private final JTextField fTitle = new JTextField("Book Detail");
    private final BookController bookController = new BookController();
    
    public ShowBookDetail(Book book) {
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2); // Adjust the insets to minimize the gap
        gbc.anchor = GridBagConstraints.WEST;

        JLabel imgIcon = new JLabel(new ImageIcon(new ImageIcon(book.getPicPath()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
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

        if (SingletonManager.getInstance().getPerson() != null) {
            if (SingletonManager.getInstance().getPerson() instanceof User) {
                BookController bc = new BookController();
                User userSession = ((User) SingletonManager.getInstance().getPerson());
                if (bc.ableToBorrow(book, userSession)) {
                    JButton borrow = new JButton("Borrow");
                    borrow.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (bc.borrowBook(book, userSession)) {
                                JOptionPane.showMessageDialog(null, "Berhasil Meminjam", "Success", JOptionPane.PLAIN_MESSAGE);
                                new ShowBookDetail(book);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal Meminjam", "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    });
                    gbc.gridy++;
                    bookPanel.add(borrow, gbc);
                } else if (bc.ableToQueue(book, userSession)) {
                    JButton queue = new JButton("Queue");
                    queue.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (bc.addBookQueue(book, userSession)) {
                                JOptionPane.showMessageDialog(null, "Berhasil Mengantri", "Success", JOptionPane.PLAIN_MESSAGE);
                                new ShowBookDetail(book);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal Meminjam", "Error", JOptionPane.PLAIN_MESSAGE);
                            }
                        }
                    });
                    gbc.gridy++;
                    bookPanel.add(queue, gbc);
                } else {
                    if (bc.checkIfBookCurrentlyBorrowed(book, userSession)) {
                        JButton returnBook = new JButton("Return");
                        returnBook.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (bc.returnBook(bc.getBorrowedBook(book, userSession))) {
                                    JOptionPane.showMessageDialog(null, "Berhasil Mengembalikan Buku", "Success", JOptionPane.PLAIN_MESSAGE);
                                    new ShowBookDetail(book);
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Gagal Mengembalikan Buku", "Error", JOptionPane.PLAIN_MESSAGE);
                                }
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
                JButton addReviewButton = new JButton("Add Review");
                gbc.gridy++;
                bookPanel.add(addReviewButton, gbc);

                addReviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Periksa apakah pengguna dapat memberikan review
                        if (!bc.checkUnableToReview(userSession, book.getIsbn())) {
                            // Tampilkan dialog untuk menambahkan review
                            JPanel reviewPanel = new JPanel();
                            reviewPanel.setLayout(new GridLayout(3, 1));

                            // Area teks untuk konten review
                            JTextArea reviewTextArea = new JTextArea(5, 20);
                            reviewTextArea.setLineWrap(true);
                            JScrollPane scrollPane = new JScrollPane(reviewTextArea);
                            reviewPanel.add(scrollPane);

                            // Pilihan rating
                            String[] ratingOptions = {"1", "2", "3", "4", "5"};
                            JComboBox<String> ratingComboBox = new JComboBox<>(ratingOptions);
                            reviewPanel.add(new JLabel("Rating:"));
                            reviewPanel.add(ratingComboBox);

                            int result = JOptionPane.showConfirmDialog(null, reviewPanel, "Add Review",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                            // Jika pengguna menekan tombol "Submit"
                            if (result == JOptionPane.OK_OPTION) {
                                // Ambil informasi review dari area teks dan pilihan rating
                                String reviewContent = reviewTextArea.getText();
                                int rating = Integer.parseInt((String) ratingComboBox.getSelectedItem().toString());

                                // Buat objek Review
                                Review review = new Review(userSession.getId(), rating, LocalDate.now(), reviewContent);

                                // Tambahkan review ke database
                                boolean success = bookController.addBookReview(review, book.getIsbn(), userSession);

                                // Perbarui tampilan jika penambahan review berhasil
                                if (success) {
                                    JOptionPane.showMessageDialog(null, "Review added successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                                    // Perbarui tampilan detail buku untuk menampilkan review yang baru ditambahkan
                                    new ShowBookDetail(book);
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to add review", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Anda tidak dapat memberikan review untuk buku ini.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        }

        ArrayList<Review> reviews = bookController.getBookReview(book);
        if (reviews != null && !reviews.isEmpty()) {
            gbc.gridy++;
            JLabel reviewTitleLabel = new JLabel("Reviews:");
            bookPanel.add(reviewTitleLabel, gbc);

            for (Review review : reviews) {
                gbc.gridy++;
                
                JLabel reviewLabel = new JLabel(review.getDate() + ": " + review.getContent() + " - Rating: " + review.getRating());
                bookPanel.add(reviewLabel, gbc);
            }
        }

        JScrollPane scroll = new JScrollPane(bookPanel);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(scroll);
        setTitle(fTitle.getText());
        setSize(400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
