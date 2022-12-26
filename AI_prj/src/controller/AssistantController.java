package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import model.VoiceModel;
import view.AssistantView;

public class AssistantController implements ActionListener {
	private AssistantView view;
	private VoiceModel model;
	private StringBuffer log = new StringBuffer();

	public AssistantController(VoiceModel model) {
		this.model = model;
		this.view = new AssistantView(this, model);
		view.createView();
		log.append("-Assistant: Hello I'm Assistant \n");
		log.append("-Assistant: Talk to me by select the microphone \n");
		view.getOutPanel().setText(log.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = "";

		if (e.getActionCommand().equals("record")) {
			System.out.println("record");
			log.append("-Assistant: Is recording \n");
			result = model.getResult();
			if (result != null || result != "") {
				log.append("-You just said: " + model.getSpeechRecognitionResult() + "\n");
				log.append("-Result: " + result + "\n");
				view.getOutPanel().setText(log.toString());
				result = "";

			}
		}

	}

}
