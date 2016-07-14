package test.java;

import main.java.Analyzer;
import junit.framework.TestCase;

public class AnalyzerTest extends TestCase {
	Analyzer tester = new Analyzer();
	String exampleString = "[200][http://apis.solbox.com/search/knowledge?apikey=23jf&q=daum][IE][2009-06-10 08:00:00]";
	
	public String stringArraytoString(String[] toChange){
		String changedString = "";
		for(int i=0; i <= toChange.length-1; i++){
			changedString = changedString + toChange[i];
			changedString = changedString + " ";
		}		
		return changedString;
	}
	
	public void testFirstLastLetterCut() {
		String test;
		test = Analyzer.firstLastLetterCut(exampleString);
		assertEquals("200][http://apis.solbox.com/search/knowledge?apikey=23jf&q=daum][IE][2009-06-10 08:00:00", test);
	}
	
	public void testStringDivider() {
		String[] dividedArray = Analyzer.stringDivider(exampleString);
		assertEquals("200 http://apis.solbox.com/search/knowledge?apikey=23jf&q=daum IE 2009-06-10 08:00:00 ", stringArraytoString(dividedArray));
		}
	
	public void testAnalyzeStatus() {
		String[] dividedArray = Analyzer.stringDivider(exampleString);
		assertEquals("200 http://apis.solbox.com/search/knowledge?apikey=23jf&q=daum IE 2009-06-10 08:00:00 ", stringArraytoString(dividedArray));
		}
}
