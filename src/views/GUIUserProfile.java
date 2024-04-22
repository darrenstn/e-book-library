package views;

import controllers.Profile;
import models.Person;
import models.User;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.PriorityBlockingQueue;

public class GUIUserProfile {
    private JFrame frame;
    private JPanel panel;
    private JLabel title;
    private JLabel nama;
    private JLabel email;
    private JLabel notelp;
    private JLabel pass;
    private JLabel bioLabel;
    private JLabel imageLabel;
    private JTextField fieldNama;
    private JTextField fieldEmail;
    private JTextField fieldNotelp;
    private JPasswordField fieldPassword;
    private JTextField fieldBio;
    private JButton editImage;
    private JButton btnedit;
    private JButton save;
    private JButton back;
    private Profile profile;
    private Person currentUser;
    private String userName;
    private String newPicPath;

    public GUIUserProfile(Person currentUser) {
        this.currentUser = currentUser;        
        frame = new JFrame("User Profile");
        frame.setSize(600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title = new JLabel("Profile");
        title.setFont(new java.awt.Font("Bookman Old Style", 1, 24));
        panel.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        nama = new JLabel("Nama:");
        nama.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        fieldNama = new JTextField();
        fieldNama.setFont(new java.awt.Font("Bookman Old Style", 1, 14));
        panel.add(fieldNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 300, 20));
        fieldNama.setEnabled(false);

        email = new JLabel("Email:");
        email.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        fieldEmail = new JTextField();
        fieldEmail.setFont(new java.awt.Font("Bookman Old Style", 1, 14));
        panel.add(fieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 300, 20));
        fieldEmail.setEnabled(false);

        notelp = new JLabel("No.Telp:");
        notelp.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(notelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        fieldNotelp = new JTextField();
        fieldNotelp.setFont(new java.awt.Font("Bookman Old Style", 1, 14));
        panel.add(fieldNotelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 300, 20));
        fieldNotelp.setEnabled(false);

        pass = new JLabel("Password:");
        pass.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        fieldPassword = new JPasswordField();
        panel.add(fieldPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 300, 20));
        fieldPassword.setEnabled(false);        

        // Taro Bio
        bioLabel = new JLabel("Bio:");
        bioLabel.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(bioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        fieldBio = new JTextField();
        fieldBio.setFont(new java.awt.Font("Bookman Old Style", 1, 14));
        panel.add(fieldBio, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 300, 20));
        fieldBio.setEnabled(false);

        // Taro Image                 
        ImageIcon icon = new ImageIcon(currentUser.getPicPath());
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        nama = new JLabel("Foto Profil:");
        nama.setFont(new java.awt.Font("Bookman Old Style", 1, 18));
        panel.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        // Taro button edit image
        userName = currentUser.getName();
        editImage = new JButton("Edit Image");        
        editImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profile profile = new Profile();
                newPicPath = profile.uploadFile(userName);                
                ImageIcon newIcon = new ImageIcon(newPicPath);
                Image newImage = newIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(newImage));
            }
        });
        panel.add(editImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, -1, -1));
        editImage.setEnabled(false);

        btnedit = new JButton("Edit Profile");
        btnedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableFields();
            }
        });
        panel.add(btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, -1, -1));

        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
        save.setEnabled(false);
        panel.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 560, -1, -1));

        back = new JButton("Kembali");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Home();
            }
        });
        panel.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 560, -1, -1));

        frame.add(panel);
        frame.setVisible(true);

        profile = new Profile();

        // Memanggil metode populateFields() saat GUIUserProfile dibuat
        populateFields();
    }

    private void populateFields() {
        // Mengisi bidang-bidang profil dengan informasi dari currentUser
        fieldNama.setText(currentUser.getName());
        fieldEmail.setText(currentUser.getEmail());
        fieldNotelp.setText(currentUser.getPhone());
        fieldPassword.setText(currentUser.getPassword());  
        fieldBio.setText(((User)currentUser).getBio());        
    }

    private void enableFields() {
        // Mengaktifkan bidang-bidang untuk diedit
        save.setEnabled(true);
        back.setEnabled(false);
        fieldNama.setEnabled(false);
        fieldEmail.setEnabled(true);
        fieldNotelp.setEnabled(true);
        fieldPassword.setEnabled(true);
        fieldBio.setEnabled(true);
        editImage.setEnabled(true);
    }

    private void updateProfile() {
        // Mengambil nilai dari bidang-bidang profil yang telah diubah
        currentUser.setName(fieldNama.getText());
        currentUser.setEmail(fieldEmail.getText());
        currentUser.setPhone(fieldNotelp.getText());
        currentUser.setPassword(new String(fieldPassword.getPassword()));
        ((User)currentUser).setBio(fieldBio.getText());
        currentUser.setPicPath(newPicPath);
        // ImageIcon newIcon = new ImageIcon(newPicPath);
        // Image newImage = newIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        // imageLabel.setIcon(new ImageIcon(newImage));
        // Memperbarui profil pengguna
        boolean success = profile.updateProfile(currentUser);

        if (success) {
            // Jika pembaruan berhasil, menonaktifkan tombol 'Save' dan mengaktifkan tombol 'Kembali'
            save.setEnabled(false);
            back.setEnabled(true);
            fieldNama.setEnabled(false);
            fieldEmail.setEnabled(false);
            fieldNotelp.setEnabled(false);
            fieldPassword.setEnabled(false);
            fieldBio.setEnabled(false);
            editImage.setEnabled(false);
        } else {
            // Menampilkan pesan kesalahan jika gagal memperbarui profil
            JOptionPane.showMessageDialog(null, "Gagal memperbarui profil!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
