package mine.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -3502018046302814142L;
    private JPanel panel;
    private JScrollPane ansScroll;
    private JScrollPane inputScroll;
    private JTextField reField;
    private JTextField imField;
    private JLabel nField;
    private JButton submit;
    private JButton btnFFT;
    private JButton btnIFFT;
    private JTextArea inputArea;
    private JTextArea ansArea;

    private Main() {
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main f = new Main();
                f.setTitle("FFT");
                f.setSize(800, 1000);
                f.setLayout(new FlowLayout());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }
}
