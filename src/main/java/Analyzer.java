package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Analyzer {
	
	private int status10, status200, status404, countIE, countFirefox, countSafari, countChrome, countOpera, countRepeat;
	
	List<String> timeList = new ArrayList<String>();
	List<String> apiKeyList = new ArrayList<String>();
	List<String> apiServiceIDList = new ArrayList<String>();
	
	/*
	 *  ����׸��� ����Ʈ�� ���� �Ŀ� �۾��ϱ� �� �����ϱ� ���ؼ� string array�� ��ȯ���ִ� �޼ҵ��Դϴ�.
	 *  
	 *  @param List ListtoChange �� ������ ����ִ� list�Ķ���ͷ� ������ �ɴϴ�.
	 *  @return String[] stringArray list�� string array�� return�մϴ�.
	 */
	//http://stackoverflow.com/questions/5374311/convert-arrayliststring-to-string-array
	public static String[] toStringArray(List listToChange){
		String[] stringArray = new String[listToChange.size()];
		stringArray = (String[]) listToChange.toArray(stringArray);
		return stringArray;
	}
	
	
	/*
	 *  ][�� �������� �ڸ��� ���ϱ� ���ؼ� ������ ó���� ���� �߸��� �޼ҵ��Դϴ�.
	 *  
	 *  @param String toCut �о� �� 1 ��(String)�� �ڸ��� ���� �Ķ���ͷ� ������ �ɴϴ�.
	 *  @return String cutted ó���� ������ ���ڸ� �ڸ� string�� return�մϴ�.
	 */
	public static String firstLastLetterCut(String toCut){
		String cutted;
		cutted = toCut.substring(1,toCut.length()-1);
		return cutted;
	}
	
	
	/*
	 *  ][�� �������� �ڸ��� �޼ҵ��Դϴ�.
	 *  
	 *  @param String toDivide firstLastLetterCut�޼ҵ�� �ڸ��� ������ String�� �޾Ƽ� �����ϴ�.
	 *  @return String[] dividedString String�� �ɰ� ��̸� �����մϴ�
	 */	
	public static String[] stringDivider(String toDivide){
		String[] dividedString = firstLastLetterCut(toDivide).split(Pattern.quote("]["));
		return dividedString;
	}
	
	/*
	 * ���¸� ���� �ٷ� ���� ī���͸� �����ִ� �޼����Դϴ�.
	 *  
	 *  @param string[] String[] dividedString �� ������ ����ִ� string array�� �����ɴϴ�. 
	 */
	public void analyzeStatus(String[] dividedString){
		switch (dividedString[0]){
			case "10":
				status10++;
				break;
			case "200":
				status200++;
				break;
			case "404":
				status404++;
				break;
			default:
				System.out.println("status is not inserted correctly");
		}
	}
	
	/*
	 *  URL�� �м����ִ� �޼ҵ��Դϴ�. �ùٸ� url�� �־������� Ȯ���� �ϰ�, apikey, serviceID�� �����մϴ�.
	 *  
	 *  @param String[] dividedString �� ������ ����ִ� string array�� �����ɴϴ�. 
	 */
	public void analyzeURL(String[] dividedString) throws WrongInputException{
		String normalURL = "http://apis.solbox.com/search/";
		String apiKey;
		String serviceID;
		String url = dividedString[1];
				
		if (url.indexOf(normalURL)!=-1){
			url.replace(normalURL, "");
		} else {
			throw new WrongInputException("Wrong Input");
		}		
		
		System.out.println(url);
		System.out.println(String.valueOf(url.indexOf("\\?")));
		serviceID = url.substring(0, url.indexOf(Pattern.quote("?"))-1);
		apiServiceIDList.add(serviceID);
		apiKey = url.substring(url.length()-5, url.length()-1);
		apiKeyList.add(apiKey);		
	}
	
	/*
	 *  �������� �м����ִ� �޼ҵ��Դϴ�. �������� ���� ī��Ʈ�� ������ŵ�ϴ�.
	 *  
	 *  @param String[] dividedString �� ������ ����ִ� string array�� �����ɴϴ�. 
	 */
	public void analyzeBrowser(String[] dividedString){
		switch (dividedString[2]){
			case "IE":
				countIE++;
				break;
			case "Firefox":
				countFirefox++;
				break;
			case "Safari":
				countSafari++;
				break;
			case "Chrome":
				countChrome++;
				break;
			case "Opera":
				countOpera++;
				break;
			default:
				System.out.println("browser is not inserted correctly");
		}
		//int sum = countIE + countFirefox+ countSafari + countChrome+countOpera;
	}
	
	/*
	 *  �������� �м����ִ� �޼ҵ��Դϴ�. �� �ð��� ����Ʈ�� �ֽ��ϴ�.
	 *  
	 *  @param String[] dividedString �� ������ ����ִ� string array�� �����ɴϴ�. 
	 */
	public void stackCallTime(String[] dividedString){
			timeList.add(dividedString[3]);
	}
	
	/*
	 *  �� ������ �ִ�  string[]�� �������ϱ����� hashmap���� ������ݴϴ�.
	 *  
	 *  @param String[] stringArray �ʿ������� �ִ� stringArray�� �����ɴϴ�. �ߺ� �����Ͱ� ���� �� �ֽ��ϴ�.
	 *  @return HashMap<String, Integer> �� String�� �� �� ���Ե��ִ��� Integer�� ��Ÿ���� HashMap�� ����մϴ�.
	 */
	public HashMap<String, Integer> makeHashMap(String[] stringArray){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String tempStr;
		for (int i = 0; i < stringArray.length; i++){
			tempStr = stringArray[i];
			if(map.containsKey(tempStr)){
				map.put(tempStr, map.get(tempStr)+1);
			}
			else
			{
				map.put(tempStr, 1);
			}
		}
		return map;
	}
	
	public static String stringArraytoString(String[] toChange){
		String changedString = "";
		for(int i=0; i <= toChange.length-1; i++){
			changedString = changedString + toChange[i];
			changedString = changedString + " ";
		}		
		return changedString;
	}
	
	/*
	 *  hashmap�� �ִ� �ְ���� value 3���� string[] �� ������ݴϴ�.�� �� �󵵸� string�� ���� �ٿ��� �����Դϴ�.
	 *  
	 *  @param HashMap<String, Integer> �� String�� �� �� ���Ե��ִ��� Integer�� ��Ÿ���� HashMap�� �޽��ϴ�.
	 *  @return String[] ranked �̸�+�󵵸� ������ string[]�� �޽��ϴ�.
	 */
	public String[] getTopValues(HashMap<String, Integer> rankedHashMap){
		String[] ranked = new String[3];

		for (int i = 0; i < 3; i++){
			int max = -1;
			String maxString = "";
			for(String key : rankedHashMap.keySet()){
				System.out.println("2");
				if(rankedHashMap.get(key) > max){
					max = rankedHashMap.get(key);
					System.out.println("3");
					System.out.println(rankedHashMap.get(key));
					maxString = key+String.valueOf(rankedHashMap.get(key));
				}
			}
			ranked[i] = maxString;
			rankedHashMap.remove(maxString);

			System.out.println(stringArraytoString(ranked));
		}
		return ranked;
	}
	
	/*
	 *  �� �������� �󵵸� �޾Ƽ� ��ü �ۼ�Ʈ�� �ٲ��ݴϴ�.
	 *  
	 *  @param int countIE, int countFirefox, int countSafari, int countChrome, int countOpera �� �������� �󵵸� �Ķ���ͷ� �޽��ϴ�.
	 *  @return int[] percentage �� �������� �ۼ�Ƽ�� ���� ������ array�� �����մϴ�.
	 */
	public int[] getBrowserPercentage(int countIE, int countFirefox, int countSafari, int countChrome, int countOpera){
		int sum = countIE + countFirefox + countSafari + countChrome + countOpera;
		int[] percentages = new int[5];
		percentages[0] = countIE / sum * 100;
		percentages[1] = countFirefox / sum * 100;
		percentages[2] = countSafari / sum * 100;
		percentages[3] = countChrome / sum * 100;
		percentages[4] = countOpera / sum * 100;
		return percentages;		
	}
	
	/*
	 *  getTopValues ���� key�� value�� �ٿ����� ������ key���� �����ϴ� �޼ҵ��Դϴ�.
	 *  
	 *  @param String both key�� value�� �Ѵ� ������ �ִ� string.
	 *  @return String key key���� ������ �ִ� string.
	 */
	public String onlyKey(String both){
		String key = both.substring(0, both.length()-2);
		return key;
	}
	
	/*
	 *  getTopValues ���� key�� value�� �ٿ����� ������ key���� �����ϴ� �޼ҵ��Դϴ�.
	 *  
	 *  @param String both key�� value�� �Ѵ� ������ �ִ� string.
	 *  @return String key value���� ������ �ִ� string.
	 */
	public String onlyValue(String both){
		System.out.println(both);
		String value = both.substring(both.length()-2, both.length()-1);
		return value;
	}
	
	/*
	 *  getTopValues ���� key�� value�� �ٿ����� ������ key���� �����ϴ� �޼ҵ��Դϴ�.
	 *  
	 *  @param String both key�� value�� �Ѵ� ������ �ִ� string.
	 *  @return String key value���� ������ �ִ� string.
	 */
	public String maxAPIKeyString(String[] maxValues){
		String maxAPISentence = "�ִ�ȣ�� APIKEY \n" + onlyKey(maxValues[0]);
		return maxAPISentence;
	}
	
	/*
	 *  output�� ���� �����ڵ庰 Ƚ���� String���� ����� �޼ҵ��Դϴ�.
	 *  
	 *  @param Ŭ�󽺳���  ī���Ͱ� �ֱ� ������ ������ �Ķ���Ͱ� �ʿ� �����ϴ�. 
	 *  @return String eachstatusString �����ڵ庰 Ƚ���� String���� ����մϴ�.
	 */
	public String eachStatusString(){
		String eachStatusString = 
				"�����ڵ� �� Ƚ�� \n 10 : " + status10 + "\n" +  "200 : "+ status200 + "\n" +  "404 : "+ status404;
		return eachStatusString;
	}
	
	/*
	 *  output�� ���� �ְ� ���� APIService 3���� String���� ����� �޼ҵ��Դϴ�.
	 *  
	 *  @param String[] maxValues getTopValues�� ���� string[]�� �Ķ���ͷ� �޽��ϴ�. 
	 *  @return String topThreeAPIServices �ְ� ���� APIService 3����  String���� ����մϴ�.
	 */
	public String topThreeAPIService(String[] maxValues){
		String topThreeAPIServices = 
				"���� 3���� API ServiceID�� ������ ��û �� \n" + maxValues[0] + " : " + "\n" + maxValues[1] + status200 + "\n" + maxValues[2];
		return topThreeAPIServices;
	}
	
	/*
	 *  output�� ���� �ְ� ���� ��ũ�ð��� string���� ����� �޼ҵ��Դϴ�.
	 *  
	 *  @param String[] maxValues getTopValues�� ���� string[]�� �Ķ���ͷ� �޽��ϴ�. 
	 *  @return String peakTime �ְ� ���� �ð���  String���� ����մϴ�.
	 */
	public String peakTimeString(String[] maxValues){
		String peakTime = 
				"��ũ �ð��� \n" + onlyKey(maxValues[0]);
		return peakTime;
	}
	
	/*
	 *  output�� ���� �� �������� �ۼ�Ʈ�� String���� ����� �޼ҵ��Դϴ�.
	 *  
	 *  @param Int[] percentages getBrowserPercentage�� ���� int[]�� �Ķ���ͷ� �޽��ϴ�. 
	 *  @return String browserPercentages �� ��������� ������� String���� ����մϴ�..
	 */
	public String browserPercentageString(int[] percentages){
		String browserPercentages = 
				"�� ������ �� ����� \n" + "IE : " + percentages[0] + "%" + "\n"+ "Firefox : " + percentages[1] + "%" + "\n"
		+ "Safari : " + percentages[2] + "%" + "\n"+ "Chrome : " + percentages[3] + "%" + "\n"+ "Opera : " + percentages[4] + "%";
		return browserPercentages;
	}
	
	public Analyzer reader() throws FileNotFoundException, WrongInputException{
		Analyzer reader = new Analyzer();
			Scanner s = new Scanner(new File("C:\\Users\\Administrator\\Downloads\\input.log"));
		    String available;
		    while(s.hasNextLine()) {
		    	available = s.nextLine();    	
				String[] tempStringArray = stringDivider(available);
				reader.analyzeStatus(tempStringArray);
				reader.analyzeURL(tempStringArray);
				reader.analyzeBrowser(tempStringArray);
				reader.stackCallTime(tempStringArray);
				countRepeat++;
		
		        }
		    s.close();
		return reader;	
	}
	
	
	public void fileOutput(Analyzer reader){
		String OUTPUT_FILE = "C:\\Users\\Administrator\\Downloads\\output.log";
		String maxAPIKeyString = reader.maxAPIKeyString(getTopValues(makeHashMap(toStringArray(apiKeyList))));
		String eachStatusString = reader.eachStatusString();
		String topThreeAPIService = reader.topThreeAPIService(getTopValues(makeHashMap(toStringArray(apiServiceIDList))));
		String peakTime = reader.peakTimeString(getTopValues(makeHashMap(toStringArray(timeList))));
		String browserPercentageString = reader.browserPercentageString(getBrowserPercentage(reader.countIE, reader.countFirefox, reader.countSafari, reader.countChrome, reader.countOpera));
		
		try(OutputStreamWriter outWriter = new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE), "UTF-8")){
			outWriter.write(maxAPIKeyString);
			outWriter.write(eachStatusString);
			outWriter.write(topThreeAPIService);
			outWriter.write(peakTime);
			outWriter.write(browserPercentageString);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) throws FileNotFoundException, WrongInputException {
		Analyzer main = new Analyzer();
		main.fileOutput(main.reader());	
	}
}
