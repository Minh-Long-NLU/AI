package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.AssistantController;

public class AssistantFrame extends JFrame {
	JTextArea outPanel;
	JButton recordBtn;

	public AssistantFrame() {
		init();
	}

	public void init() {

		setBounds(100, 100, 450, 576);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setBackground(Color.white);
		panel.setBorder(null);

		JLabel titleLabel = new JLabel("ASSISTANT");
		titleLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		panel.add(titleLabel);

		JPanel funcPanel = new JPanel();
		funcPanel.setBackground(Color.white);
		getContentPane().add(funcPanel, BorderLayout.SOUTH);

		recordBtn = new JButton("");
		recordBtn.setActionCommand("record");
		recordBtn.setForeground(Color.LIGHT_GRAY);
		recordBtn.setBorder(null);
		recordBtn.setFocusable(false);
		recordBtn.setIcon(new ImageIcon(test.class.getResource("/icon/voice-recognition.png")));
		funcPanel.add(recordBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		outPanel = new JTextArea();
		outPanel.setFont(new Font("Calisto MT", Font.PLAIN, 20));
		outPanel.setBackground(Color.white);
		outPanel.setBorder(null);
		scrollPane.setViewportView(outPanel);

	}

	public JTextArea getOutPanel() {
		return outPanel;
	}

	public JButton getRecordBtn() {
		return recordBtn;
	}
//	public static void main(String[] args) {
//		AssistantFrame frame = new AssistantFrame();
//		frame.setVisible(true);
//	}
}
