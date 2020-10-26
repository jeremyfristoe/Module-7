
//Written by Jeremy Fristoe
//CEN-3024C-17056 Module 7 v5, 10/25/20
// This program references and processes specific areas of text on a website, outputting the frequency
// of occurrences for each word and sorting based on which words are most frequently used.  Module 6
// introduced an overlaid GUI for basic user interaction.  Module 7 includes JUnit testing.

package m7v5;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TextAnalyzer extends Application {
	
	public void start(Stage primaryStage) {

		try {
			// Primary stage settings
			primaryStage.setTitle("Text Analyzer Program");
			
			
			// Pane settings
			AnchorPane pane = new AnchorPane();
			pane.setMinHeight(800);
			pane.setMinWidth(700);
			pane.setStyle("-fx-border-color: lightblue; -fx-border-width: 35px 35px 35px 35px");

			
			// Scene settings
			Scene scene = new Scene(pane);
			
			
			// "Welcome screen" settings
			Label welcome = new Label("                              Welcome!\n\nPlease enter your name to launch the program.");
	        welcome.setTextFill(Color.WHITE);
			welcome.setStyle("-fx-background-color: navy");
			welcome.setFont(Font.font("Times", 28));
			welcome.setAlignment(Pos.CENTER);
			AnchorPane.setTopAnchor(welcome, 60.0);
			AnchorPane.setRightAnchor(welcome, 10.0);
			AnchorPane.setBottomAnchor(welcome, 10.0);
			AnchorPane.setLeftAnchor(welcome, 10.0);
			
			TextField nameField = new TextField();
			nameField.setFont(Font.font("Arial", 20));
			AnchorPane.setTopAnchor(nameField, 15.0);
			AnchorPane.setLeftAnchor(nameField, 10.0);
			pane.getChildren().add(nameField);
			
			
			// "Running program screen" settings
			Button thanks = new Button ("    Thanks for trying the text analyzer program!\n\n\n\n" +
					"When you are ready, please close this window\n     and the results will appear in your console.");
			        thanks.setTextFill(Color.WHITE);
					thanks.setStyle("-fx-background-color: navy");
					thanks.setFont(Font.font("Times", 28));
					thanks.setAlignment(Pos.CENTER);
					AnchorPane.setTopAnchor(thanks, 60.0);
					AnchorPane.setRightAnchor(thanks, 10.0);
					AnchorPane.setBottomAnchor(thanks, 10.0);
					AnchorPane.setLeftAnchor(thanks, 10.0);
		        thanks.setOnAction(new EventHandler<ActionEvent>() {
		            public void handle(ActionEvent event) {
		            }
		        });
			
			
			// Launch program
	        nameField.setOnAction(new EventHandler<ActionEvent>() {
	        	public void handle(ActionEvent event) {
	        		if(nameField.getText().isEmpty()) {
	        			return;
	        		}
	        		else {
		    			try {
							Thread.sleep(600);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	        		}

	        		welcome.setVisible(false);
	        		nameField.setVisible(false);
	    			pane.getChildren().add(thanks);
	        	}
	        });


			// Initiate welcome
			pane.getChildren().add(welcome);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
	
		Map<String, Word> countMap = new HashMap<String, Word>();
		Application.launch(args);

		// Connecting to the site and pulling the data
		System.out.println("Retrieving info from website and analyzing data. One moment, please...\n");

		// To get the list of all links from a website
		Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").timeout(25000).get();
		
		//Getting the actual text from the page, excluding the HTML
		String text = doc.body().getElementsByTag("h1").text() + " " + doc.body().getElementsByTag("h4").text() + " " 
				+ doc.body().getElementsByTag("h3").text() + " " + doc.body().getElementsByTag("p").text();

		System.out.println("#       Words\n-------------------");

		//Create BufferedReader so the words can be counted
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes())));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] words = line.split("[^a-zA-z]");
				for (String word : words) {
					if ("".equals(word)) {
						continue;
					}
					Word wordObj = countMap.get(word);
					if (wordObj == null) {
						wordObj = new Word();
						wordObj.word = word;
						wordObj.count = 0;
						countMap.put(word, wordObj);
					}
					wordObj.count++;
				}

		}

	    reader.close();
	
	    SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
	    int i = 0;
	    int maxWordsToDisplay = 20;
	
	    for (Word word : sortedWords) {
	        if (i >= maxWordsToDisplay) {
	             break;
	        }
	        else {
	            System.out.println(word.count + "\t" + word.word);
	            i++;
	        }
	
		}
	
	}

	public static class Word implements Comparable<Word> {
		String word;
		int count;

		public int hashCode() { return word.hashCode(); }

		public boolean equals(Object obj) { return word.equals(((Word)obj).word); }
	     
		public int compareTo(Word b) { return b.count - count; }

	}

}