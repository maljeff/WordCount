package synalogik.restapi;

import static org.junit.Assert.*;

import org.junit.Test;

public class RestAPITest extends RestAPI {

	@Test
	public void testWordCount() {
		wordCount("Hello world & good morning. The date is 18/05/2016");
	}

	@Test
	public void testTextFromURL() {
		textFromURL("https://janelwashere.com/files/bible_daily.txt");
	}

}
