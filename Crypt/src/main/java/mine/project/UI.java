package mine.project;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;

/**
 * UI
 */
public class UI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField pwdField;
    private JButton btnEncrypt;
    private JButton btnDecrypt;

    public UI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String srcFile = textField.getText();
                String encryFile = textField.getText() + ".enc";
                String pwd = pwdField.getText();
                try {
                    new App(pwd).encry(new File(srcFile), new File(encryFile));
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        contentPane.add(btnEncrypt);

        textField = new JTextField();
        textField.setBounds(10, 100, 100, 20);
        contentPane.add(textField);

        JButton btnChooseFile = new JButton("...");
        btnChooseFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("C:");
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    textField.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
        btnChooseFile.setBounds(378, 91, 40, 27);
        contentPane.add(btnChooseFile);

    }

    private void init() {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                UI ui = new UI();
                ui.setVisible(true);
            }
        });
    }
}