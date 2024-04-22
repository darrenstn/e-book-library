/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.AdminController;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import models.Book;
import models.enums.Category;
import models.enums.Genre;

/**
 *
 * @author marti
 */
public class AddBook extends JFrame {

    private AdminController adminController;

    public AddBook() {
        adminController = new AdminController();

        // Membuat panel baru untuk formulir penambahan buku
        JPanel addBookPanel = new JPanel();
        addBookPanel.setLayout(new GridLayout(0, 1, 5, 5));

        // Formulir untuk menambahkan buku
        JTextField isbnField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField picPathField = new JTextField();
        JComboBox<Genre> genreComboBox = new JComboBox<>(Genre.values());
        JComboBox<Category> categoryComboBox = new JComboBox<>(Category.values());
        JLabel picPathLabel = new JLabel("Picture Path: ");
        JButton browseBtn = new JButton("Browse");

        browseBtn.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                picPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Menambahkan komponen ke panel
        addBookPanel.add(new JLabel("ISBN:"));
        addBookPanel.add(isbnField);
        addBookPanel.add(new JLabel("Year:"));
        addBookPanel.add(yearField);
        addBookPanel.add(new JLabel("Title:"));
        addBookPanel.add(titleField);
        addBookPanel.add(new JLabel("Genre:"));
        addBookPanel.add(genreComboBox);
        addBookPanel.add(new JLabel("Category:"));
        addBookPanel.add(categoryComboBox);
        addBookPanel.add(new JLabel("Author:"));
        addBookPanel.add(authorField);
        addBookPanel.add(new JLabel("Stock:"));
        addBookPanel.add(stockField);
        addBookPanel.add(picPathLabel);
        addBookPanel.add(picPathField);
        addBookPanel.add(browseBtn);

        // Menampilkan panel dalam dialog
        int result = JOptionPane.showConfirmDialog(null, addBookPanel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Simpan data buku ke database
            try {
                int year = Integer.parseInt(yearField.getText());
                int stock = Integer.parseInt(stockField.getText());

                Book newBook = new Book(
                        isbnField.getText(),
                        year,
                        titleField.getText(),
                        (Genre) genreComboBox.getSelectedItem(),
                        (Category) categoryComboBox.getSelectedItem(),
                        authorField.getText(),
                        stock,
                        picPathField.getText()
                );

                // Panggil method dari BookController untuk menyimpan buku baru
                adminController.addBook(newBook);

                // Tampilkan pesan sukses
                JOptionPane.showMessageDialog(null, "Book added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for year or stock. Please enter valid numbers.");
            }
        }
    }
}
