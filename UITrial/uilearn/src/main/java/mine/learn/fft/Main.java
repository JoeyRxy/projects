package mine.learn.fft;

import java.awt.Color;
// import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import mine.learn.fft.fft.Complex;
import mine.learn.fft.fft.FFT;

public class Main extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 6717779306954794357L;
	private JPanel contentPane;
	private JTextField reField;
	private JTextField imField;
	private JTextField nField;
	private JTextArea inputArea = new JTextArea();
	private JTextArea ansArea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static Complex[] getCArray(String s) {
		String[] list = s.split("\n");
		Complex[] res = new Complex[list.length];
		int i = 0;
		for (String cStr : list) {
			if (cStr != null) {
				res[i++] = Complex.complexParser(cStr);
			}
		}

		return res;
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane inputPanel = new JScrollPane();
		inputPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JScrollPane ansPanel = new JScrollPane();
		ansPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		reField = new JTextField();
		reField.setColumns(10);
		reField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					imField.requestFocus();
				}
			}
		});

		JLabel lblJ = new JLabel("+ j");

		imField = new JTextField();
		imField.setColumns(10);
		imField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					double re = Double.parseDouble(reField.getText());
					double im = Double.parseDouble(imField.getText());
					Complex c = new Complex(re, im);

					int tmp = Integer.parseInt(nField.getText()) + 1;
					nField.setText(Integer.toString(tmp));

					inputArea.append(c.toString() + "\n");

					reField.setText("");
					imField.setText("");
					reField.requestFocus();
				}
			}
		});

		JButton btnFft = new JButton("FFT");
		btnFft.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				reField.setText("");
				imField.setText("");
				String text = inputArea.getText();
				if (text.length() == 0) {
					ansArea.setText("");
					return;
				}
				Complex[] cArray = getCArray(text);
				if (!FFT.isPower2(cArray.length)) {
					JOptionPane.showMessageDialog(null, "the length of the array `N` must be power of 2!");
					return;
				}
				Complex[] res = FFT.fft(cArray);
				StringBuilder builder = new StringBuilder();
				for (Complex c : res) {
					builder.append(c).append("\n");
				}
				ansArea.setText(builder.toString());
			}
		});

		JButton btnIfft = new JButton("IFFT");
		btnIfft.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				reField.setText("");
				imField.setText("");
				String text = inputArea.getText();
				if (text.length() == 0) {
					ansArea.setText("");
					return;
				}
				Complex[] cArray = getCArray(inputArea.getText());
				if (!FFT.isPower2(cArray.length)) {
					JOptionPane.showMessageDialog(null, "the length of the array `N` must be power of 2!");
					return;
				}
				Complex[] res = FFT.ifft(cArray);
				StringBuilder builder = new StringBuilder();
				for (Complex c : res) {
					builder.append(c).append("\n");
				}
				ansArea.setText(builder.toString());
			}
		});

		JButton btn = new JButton("Edit");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btn.getText().equals("Edit")) {
					btn.setText("Commit");
					inputArea.setEditable(true);
				} else {
					btn.setText("Edit");
					Complex[] tmp = getCArray(inputArea.getText());
					nField.setText(Integer.toString(tmp.length));
					inputArea.setEditable(false);
				}
			}
		});

		JLabel lblInput = new JLabel("Input :");

		JLabel lblTheInputs = new JLabel("The Inputs");

		JLabel lblTheAns = new JLabel("The Ans");

		JLabel lblTotalCount = new JLabel("Sequence Length :");

		nField = new JTextField();
		nField.setForeground(Color.BLUE);
		nField.setFont(new Font("宋体", Font.PLAIN, 15));
		nField.setEditable(false);
		nField.setText("0");
		nField.setColumns(10);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputArea.setText("");
				nField.setText("0");
				ansArea.setText("");
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addGap(174)
												.addComponent(lblInput).addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(reField, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblJ))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(28)
												.addGroup(gl_contentPane
														.createParallelGroup(Alignment.LEADING).addComponent(btn)
														.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 291,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblTheInputs).addComponent(btnClear))))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
								.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(btnFft)
										.addComponent(ansPanel, GroupLayout.PREFERRED_SIZE, 281,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnIfft).addComponent(lblTheAns))
								.addGap(29))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(imField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(495, Short.MAX_VALUE)
						.addComponent(lblTotalCount).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nField, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE).addGap(41)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(17)
				.addGroup(gl_contentPane
						.createParallelGroup(Alignment.BASELINE).addComponent(lblTotalCount).addComponent(nField,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(reField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblJ).addComponent(lblInput).addComponent(imField, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(53)
				.addGroup(gl_contentPane
						.createParallelGroup(Alignment.BASELINE).addComponent(lblTheInputs).addComponent(lblTheAns))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
						.addComponent(ansPanel, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(33).addComponent(btnFft)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnIfft))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(24).addComponent(btn)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear)))
				.addGap(15)));

		inputArea.setToolTipText("The recognizable Complex Pattern is: a +\\- j b;\na+\\-bj is invalid");
		inputArea.setEditable(false);
		inputPanel.setViewportView(inputArea);

		ansArea.setEditable(false);
		ansPanel.setViewportView(ansArea);
		contentPane.setLayout(gl_contentPane);
	}
}
