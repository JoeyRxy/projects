package mine.project.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.util.*;

import mine.project.Complex;
import mine.project.FFT;

/**
 * Main
 * <p>
 * TODO:模块之间的通信问题？
 */
public class Main extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField reField = new JTextField("0");
    private JTextField imField = new JTextField("0");
    private JTextArea ansArea = new JTextArea("null");
    private JButton btn = new JButton("计算");
    private JTextField nField = new JTextField("0");

    public Main() {
        List<Complex> input = new ArrayList<>(8);
        reField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // TODO:按下回车跳转到输入 虚数 的部分

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        imField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // TODO：跳到输入real的部分；
                    double re = Double.parseDouble(reField.getText());
                    double im = Double.parseDouble(imField.getText());
                    input.add(new Complex(re, im));
                    Integer tmp = Integer.parseInt(nField.getText()) + 1;
                    nField.setText(tmp.toString());
                }
            }
        });

        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FFT.isPower2(input.size())) {
                    JOptionPane.showConfirmDialog(null, "必须是2的幂次，是否补零至大于" + nField.getText() + "的数字");
                    // TODO:使用比N大的最小的2的幂次进行fft计算
                }
                Complex[] ans = FFT.fft((Complex[]) input.toArray());

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main f = new Main();
                f.setTitle("FFT");
                f.setSize(800, 1000);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

}