package mine.learn.graphtheory.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * MainFrame
 */
public class MainFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 7950635014321495951L;

    public MainFrame() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setTitle("MainFrame");
                frame.setSize(800, 1000);
                frame.setLayout(new FlowLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
