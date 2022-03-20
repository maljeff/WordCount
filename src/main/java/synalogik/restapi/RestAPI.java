package synalogik.restapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/file")
public class RestAPI {
	
	@GET
	@Path("/{text}")
	public void wordCount(@PathParam("text") String text) {
		
		textSummary(text);
		
	}

	@GET
	@Path("/{path}")
	public void textFromURL(@PathParam("url") String url) {
		
	    StringBuilder contentBuilder = new StringBuilder();
	    String str ="";
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(url));
	        while ((str = in.readLine()) != null) {
	            contentBuilder.append(str);
	        }
	       str = in.readLine();
	       in.close();
	       } catch (IOException e) {
	           System.out.println(e.toString());
	       }
	    textSummary(contentBuilder.toString());
	}

	private void textSummary(String text) {
		Integer words = null;
		Double wordLength = null;
		Integer letters = 0;
		Map<Integer, Integer> wordLetters = new TreeMap<Integer, Integer>();
		
		// Count the number of words
		String[] wordArray = text.split("[ .]+");
		words = wordArray.length;
		
		// Count the number of letters
		for (String s : wordArray) {
			letters = letters + s.length();
		}
		
		// Get the average word length
		wordLength = Double.valueOf(letters) / words;
		
		// Count the number of letters per word
		wordLetters = countWords(wordArray);
		
		// Output to the console
		System.out.println("Word count = " + words);
		System.out.println("Average word length = " + String.format("%.3f",  wordLength));
		for (Integer key : wordLetters.keySet()) {
			System.out.println("Number of words of length " + key + " is " + wordLetters.get(key));
		}
		
		// Get the most common length
		getLength(wordLetters);
	}
	
	private void getLength(Map<Integer, Integer> wordLetters) {
		Integer frequentLength = 0;
		ArrayList<Integer> longestWordsCount = new ArrayList<Integer>();
		for (Integer key : wordLetters.keySet()) {
			if (wordLetters.get(key) > frequentLength) {
				frequentLength = wordLetters.get(key);
				longestWordsCount.clear();
				longestWordsCount.add(key);
			} else if (wordLetters.get(key) == frequentLength) {
				longestWordsCount.add(key);
			}
		}
		System.out.print("The most frequently occurring word length is " + frequentLength + ", for word lengths of ");
		if (longestWordsCount.size() == 1) {
			System.out.print(longestWordsCount.get(0));
		} else {
			for(int i = 0; i < longestWordsCount.size(); i++ ) {
				if (i > 0) {
					System.out.print(" & ");
				}
				System.out.print(longestWordsCount.get(i));
			}
		}
		System.out.println("");
	}

	private Map<Integer, Integer> countWords(String[] wordArray) {
		
		Map<Integer, Integer> wordLetters = new TreeMap<Integer, Integer>();
		
		for (int i = 0; i < wordArray.length; i++) {
			String word = wordArray[i];
			if (wordLetters.containsKey(word.length())) {
				wordLetters.replace(word.length(), wordLetters.get(word.length()) + 1);
			} else {
				wordLetters.put(word.length(), 1);
			}
		}
			
		return wordLetters;
	}
	

}
