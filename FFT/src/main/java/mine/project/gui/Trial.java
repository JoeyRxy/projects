package mine.project.gui;

import java.awt.FlowLayout;
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
public class Trial extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField reField = new JTextField();
    private JTextField imField = new JTextField();
    // private JScrollPane inputPane = new JScrollPane();
    private JTextArea inputArea = new JTextArea(16, 40);
    private JTextArea ansArea = new JTextArea(16, 40);
    private JButton btnFFT = new JButton("FFT");
    private JButton btnIFFT = new JButton("IFFT");
    private JTextField nField = new JTextField("0");
    private JButton submit = new JButton("Submit");

    public Trial() {
        ansArea.setEditable(false);

        List<Complex> inputList = new ArrayList<>(8);
        reField.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // TODO:按下回车跳转到输入 虚数 的部分
                    imField.requestFocus();
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
                    // IMPORTANT：跳到输入real的部分；焦点转移
                    double re = Double.parseDouble(reField.getText());
                    double im = Double.parseDouble(imField.getText());
                    Complex c = new Complex(re, im);
                    inputList.add(c);
                    reField.setText("0");
                    imField.setText("0");

                    int tmp = Integer.parseInt(nField.getText()) + 1;
                    nField.setText(Integer.toString(tmp));

                    inputArea.append(c.toString() + "\n");
                }
            }
        });

        btnFFT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FFT.isPower2(inputList.size())) {
                    JOptionPane.showConfirmDialog(null, "WARNING!\n必须是2的幂次，是否补零至大于" + nField.getText() + "的数字");
                    // TODO:使用比N大的最小的2的幂次进行fft计算
                }
                Complex[] ans = FFT.fft((Complex[]) inputList.toArray());
                StringBuilder buffer = new StringBuilder();
                for (Complex complex : ans) {
                    buffer.append(complex).append("\n");
                }
                ansArea.setText(buffer.toString());
            }
        });

        btnIFFT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FFT.isPower2(inputList.size())) {
                    JOptionPane.showConfirmDialog(null, "WARNING!\n必须是2的幂次，是否补零至大于" + nField.getText() + "的数字");
                    // TODO:使用比N大的最小的2的幂次进行fft计算
                }
                Complex[] ans = FFT.ifft((Complex[]) inputList.toArray());
                StringBuilder buffer = new StringBuilder();
                for (Complex complex : ans) {
                    buffer.append(complex).append("\n");
                }
                ansArea.setText(buffer.toString());
            }
        });
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新
                inputList.clear();
                String[] inputs = inputArea.getText().split("\n");
                for (int i = 0; i < inputs.length - 1; i++) {
                    inputList.add(Complex.complexParser(inputs[i]));
                }
            }
        });
        add(submit);
        add(btnFFT);
        add(btnIFFT);
        add(reField);
        add(imField);
        add(nField);
        add(new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        add(new JScrollPane(ansArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Trial f = new Trial();
                f.setTitle("FFT");
                f.setSize(800, 1000);
                f.setLayout(new FlowLayout());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

}