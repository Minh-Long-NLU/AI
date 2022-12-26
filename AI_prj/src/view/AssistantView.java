package view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JTextArea;

import controller.AssistantController;
import model.VoiceModel;

public class AssistantView {
	private AssistantController controller;
	private VoiceModel model;
	AssistantFrame frame;
	public AssistantView() {
	}
	
	public AssistantView(AssistantController controller, VoiceModel model) {
		super();
		this.controller = controller;
		this.model = model;
		
		
	}
	
	public void createView() {
		frame = new AssistantFrame();
		frame.setVisible(true);
		eventHanding();
	}
	
	public void eventHanding() {
		frame.recordBtn.addActionListener(controller);
	}

	public JTextArea getOutPanel() {
		return frame.getOutPanel();
	}

	public JButton getRecordBtn() {
		return frame.getRecordBtn();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VoiceModel model = new VoiceModel();
					AssistantController controller = new AssistantController(model);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
