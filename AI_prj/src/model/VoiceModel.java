package model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

public class VoiceModel {
	private String result;
	public Configuration config;
	EnglishStringToNumber stringToNumber = new EnglishStringToNumber();

	private LiveSpeechRecognizer recognizer;

	private Logger logger = Logger.getLogger(getClass().getName());

	public String speechRecognitionResult;

	private boolean ignoreSpeechRecognitionResults = false;

	private boolean speechRecognizerThreadRunning = false;

	private boolean resourcesThreadRunning;

	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

	public VoiceModel() {
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");

		config = new Configuration();
		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

		config.setGrammarPath("resource:/grammars");
		config.setGrammarName("grammar");
		config.setUseGrammar(true);

		try {
			recognizer = new LiveSpeechRecognizer(config);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		startResourcesThread();
		startSpeechRecognition();

	}

	public void init() {
	}

	public String getResult() {
		return result;
	}

	public synchronized void startSpeechRecognition() {

		if (speechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else

			eventsExecutorService.submit(() -> {

				speechRecognizerThreadRunning = true;
				ignoreSpeechRecognitionResults = false;

				recognizer.startRecognition(true);

				logger.log(Level.INFO, "You can start to speak...\n");

				try {
					while (speechRecognizerThreadRunning) {
						SpeechResult speechResult = recognizer.getResult();

						if (!ignoreSpeechRecognitionResults) {

							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								speechRecognitionResult = speechResult.getHypothesis();
								System.out.println("You said: [" + speechRecognitionResult + "]\n");
								makeDecision(speechRecognitionResult, speechResult.getWords());

							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");

					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					speechRecognizerThreadRunning = false;
				}

				logger.log(Level.INFO, "SpeechThread has exited...");

			});
	}

	public synchronized void stopIgnoreSpeechRecognitionResults() {

		ignoreSpeechRecognitionResults = false;
	}

	public synchronized void ignoreSpeechRecognitionResults() {
		ignoreSpeechRecognitionResults = true;

	}

	public void startResourcesThread() {
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			eventsExecutorService.submit(() -> {
				try {
					resourcesThreadRunning = true;
					while (true) {

						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");

						Thread.sleep(350);
					}

				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}

	public void makeDecision(String speech, List<WordResult> speechWords) {

		String[] array = speech.split("(plus|minus|multiply|division){1}");
		System.out.println(Arrays.toString(array) + " total number : " + array.length);
		if (array.length < 2)
			return;

		int number1 = stringToNumber.convert(array[0]);
		int number2 = stringToNumber.convert(array[1]);

		int calculationResult = 0;
		String symbol = "?";

		if (speech.contains("plus")) {
			calculationResult = number1 + number2;
			symbol = "+";
		} else if (speech.contains("minus")) {
			calculationResult = number1 - number2;
			symbol = "-";
		} else if (speech.contains("multiply")) {
			calculationResult = number1 * number2;
			symbol = "*";
		} else if (speech.contains("division")) {
			if (number2 == 0)
				return;
			calculationResult = number1 / number2;
			symbol = "/";
		}

		result = number1 + " " + symbol + " " + number2 + " = " + calculationResult;
		System.out.println(number1 + " " + symbol + " " + number2 + " = " + calculationResult);

	}

	public boolean getIgnoreSpeechRecognitionResults() {
		return ignoreSpeechRecognitionResults;
	}

	public boolean getSpeechRecognizerThreadRunning() {
		return speechRecognizerThreadRunning;
	}

	public String getSpeechRecognitionResult() {
		return speechRecognitionResult;
	}

	public static void main(String[] args) {
		new VoiceModel();
	}

}
