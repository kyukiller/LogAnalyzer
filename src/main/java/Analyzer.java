package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Analyzer {
	
	public double status10;

	public double status200;

	public double status404;

	public double countIE;

	public double countFirefox;

	public double countSafari;

	public double countChrome;

	public double countOpera;

	public double countRepeat;
	
	public List<String> timeList = new ArrayList<String>();
	public List<String> apiKeyList = new ArrayList<String>();
	public List<String> apiServiceIDList = new ArrayList<String>();
	
	/*
	 *  모든항목을 리스트에 넣은 후에 작업하기 더 수월하기 위해서 string array로 변환해주는 메소드입니다.
	 *  
	 *  @param List ListtoChange 각 정보가 들어있는 list파라미터로 가지고 옵니다.
	 *  @return String[] stringArray list를 string array로 return합니다.
	 */
	//http://stackoverflow.com/questions/5374311/convert-arrayliststring-to-string-array
	public static String[] toStringArray(List listToChange){
		String[] stringArray = new String[listToChange.size()];
		stringArray = (String[]) listToChange.toArray(stringArray);
		return stringArray;
	}
	
	
	/*
	 *  ][를 기준으로 자르기 편하기 위해서 각줄의 처음과 끝을 잘르는 메소드입니다.
	 *  
	 *  @param String toCut 읽어 온 1 줄(String)을 자르기 위해 파라미터로 가지고 옵니다.
	 *  @return String cutted 처음과 마지막 글자를 자른 string을 return합니다.
	 */
	public static String firstLastLetterCut(String toCut){
		String cutted;
		cutted = toCut.substring(1,toCut.length()-1);
		return cutted;
	}
	
	
	/*
	 *  ][를 기준으로 자르는 메소드입니다.
	 *  
	 *  @param String toDivide firstLastLetterCut메소드로 자르고 리던퇸 String을 받아서 나눕니다.
	 *  @return String[] dividedString String을 쪼갠 어레이를 리턴합니다
	 */	
	public static String[] stringDivider(String toDivide){
		String[] dividedString = firstLastLetterCut(toDivide).split(Pattern.quote("]["));
		return dividedString;
	}
	
	/*
	 * 상태를 보고 바로 상태 카운터를 높여주는 메서드입니다.
	 *  
	 *  @param string[] String[] dividedString 각 정보가 들어있는 string array를 가져옵니다. 
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
	 *  URL을 분석해주는 메소드입니다. 올바른 url이 주어졌는지 확인을 하고, apikey, serviceID를 추출합니다.
	 *  
	 *  @param String[] dividedString 각 정보가 들어있는 string array를 가져옵니다. 
	 */
	public void analyzeURL(String[] dividedString){
		String normalURL = "http://apis.solbox.com/search/";
		String apiKey;
		String serviceID;
		String url = dividedString[1];
		url = url.replaceFirst(normalURL, "");
		
		serviceID = url.substring(0, url.indexOf('?'));
		apiServiceIDList.add(serviceID);
		apiKey = url.substring(url.indexOf("apikey=")+7, url.indexOf("apikey=")+11);
		apiKeyList.add(apiKey);	
	}
	
	/*
	 *  브라우저를 분석해주는 메소드입니다. 브라우저에 따라 카운트를 증가시킵니다.
	 *  
	 *  @param String[] dividedString 각 정보가 들어있는 string array를 가져옵니다. 
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
	 *  브라우저를 분석해주는 메소드입니다. 각 시간을 리스트에 넣습니다.
	 *  
	 *  @param String[] dividedString 각 정보가 들어있는 string array를 가져옵니다. 
	 */
	public void stackCallTime(String[] dividedString){
			timeList.add(dividedString[3]);
	}
	
	/*
	 *  각 정보가 있는  string[]를 빈도측정하기위해 hashmap으로 만들어줍니다.
	 *  
	 *  @param String[] stringArray 필요정보가 있는 stringArray를 가져옵니다. 중복 데이터가 있을 수 있습니다.
	 *  @return HashMap<String, Integer> 각 String이 몇 번 포함되있는지 Integer로 나타내는 HashMap을 출력합니다.
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
	 *  hashmap에 있는 최고빈도의 value 3개를 string[] 로 만들어줍니다.이 때 빈도를 string의 끝에 붙여서 쓸것입니다.
	 *  
	 *  @param HashMap<String, Integer> 각 String이 몇 번 포함되있는지 Integer로 나타내는 HashMap을 받습니다.
	 *  @return String[] ranked 이름+빈도를 저장한 string[]를 받습니다.
	 */
	public String[] getTopValues(HashMap<String, Integer> rankedHashMap){
		String[] ranked = new String[3];
		System.out.println(this.timeList.toString());
		System.out.println(this.apiKeyList.toString());
		System.out.println(this.apiServiceIDList.toString());
		for (int i = 0; i < 3; i++){
			int max = -1;
			String maxString = "";
			for(String key : rankedHashMap.keySet()){
				if(rankedHashMap.get(key) > max){
					max = rankedHashMap.get(key);
					maxString = key+"value:"+String.valueOf(rankedHashMap.get(key));
					System.out.println("key:"+onlyKey(maxString));
					System.out.println("value:"+onlyValue(maxString));

				}
			}
			ranked[i] = maxString;
			rankedHashMap.remove(onlyKey(maxString));
		}
		return ranked;
	}
	
	/*
	 *  각 브라우저의 빈도를 받아서 전체 퍼센트로 바꿔줍니다.
	 *  
	 *  @param int countIE, int countFirefox, int countSafari, int countChrome, int countOpera 각 브라우저의 빈도를 파라미터로 받습니다.
	 *  @return int[] percentage 각 브라우저의 퍼센티지 값을 나열한 array를 리턴합니다.
	 */
	public double[] getBrowserPercentage(double countIE, double countFirefox, double countSafari, double countChrome, double countOpera){
		double sum = countIE + countFirefox + countSafari + countChrome + countOpera;
		double[] percentages = new double[5];
		percentages[0] = countIE / sum * 100;
		percentages[1] = countFirefox / sum * 100;
		percentages[2] = countSafari / sum * 100;
		percentages[3] = countChrome / sum * 100;
		percentages[4] = countOpera / sum * 100;
		System.out.println(String.valueOf(countIE / sum * 100));
		return percentages;		
	}
	
	/*
	 *  getTopValues 에서 key와 value를 붙여놨기 때문에 key만을 추출하는 메소드입니다.
	 *  
	 *  @param String both key와 value를 둘다 가지고 있는 string.
	 *  @return String key key만을 가지고 있는 string.
	 */
	public String onlyKey(String both){
		String key = both.substring(0, both.indexOf("value:"));
		return key;
	}
	
	/*
	 *  getTopValues 에서 key와 value를 붙여놨기 때문에 key만을 추출하는 메소드입니다.
	 *  
	 *  @param String both key와 value를 둘다 가지고 있는 string.
	 *  @return String key value만을 가지고 있는 string.
	 */
	public String onlyValue(String both){
		System.out.println(both);
		String value = both.substring(both.indexOf("value:")+6, both.length());
		return value;
	}
	
	/*
	 *  getTopValues 에서 key와 value를 붙여놨기 때문에 key만을 추출하는 메소드입니다.
	 *  
	 *  @param String both key와 value를 둘다 가지고 있는 string.
	 *  @return String key value만을 가지고 있는 string.
	 */
	public String maxAPIKeyString(String[] maxValues){
		String maxAPISentence = "최다호출 APIKEY" + System.lineSeparator() + onlyKey(maxValues[0]);
		return maxAPISentence;
	}
	
	/*
	 *  output을 위해 상태코드별 횟수를 String으로 만드는 메소드입니다.
	 *  
	 *  @param 클라스내에  카운터가 있기 때문에 별도의 파라미터가 필요 없습니다. 
	 *  @return String eachstatusString 상태코드별 횟수를 String으로 출력합니다.
	 */
	public String eachStatusString(){
		String eachStatusString = 
				"상태코드 별 횟수" + System.lineSeparator() + "10 : " + (int)status10 + System.lineSeparator() +  "200 : "+ (int)status200 + System.lineSeparator() +  "404 : "+ (int)status404;
		return eachStatusString;
	}
	
	/*
	 *  output을 위해 최고 빈도의 APIService 3개를 String으로 만드는 메소드입니다.
	 *  
	 *  @param String[] maxValues getTopValues로 뽑은 string[]를 파라미터로 받습니다. 
	 *  @return String topThreeAPIServices 최고 빈도의 APIService 3개를  String으로 출력합니다.
	 */
	public String topThreeAPIService(String[] maxValues){
		String topThreeAPIServices = 
				"상위 3개의 API ServiceID와 각각의 요청 수 " + System.lineSeparator() + onlyKey(maxValues[0]) + " : " +  onlyValue(maxValues[0]) + System.lineSeparator() +
				onlyKey(maxValues[1]) + " : "  + onlyValue(maxValues[1]) + System.lineSeparator() + onlyKey(maxValues[2]) + " : " + onlyValue(maxValues[2]);
		return topThreeAPIServices;
	}
	
	/*
	 *  output을 위해 최고 빈도의 피크시간을 string으로 만드는 메소드입니다.
	 *  
	 *  @param String[] maxValues getTopValues로 뽑은 string[]를 파라미터로 받습니다. 
	 *  @return String peakTime 최고 빈도의 시간을  String으로 출력합니다.
	 */
	public String peakTimeString(String[] maxValues){
		String peakTime = 
				"피크 시간대 " + System.lineSeparator() + onlyKey(maxValues[0]);
		return peakTime;
	}
	
	/*
	 *  output을 위해 각 브라우저의 퍼센트를 String으로 만드는 메소드입니다.
	 *  
	 *  @param Int[] percentages getBrowserPercentage로 뽑은 int[]를 파라미터로 받습니다. 
	 *  @return String browserPercentages 각 브라으주의 백분율을 String으로 출력합니다..
	 */
	public String browserPercentageString(double[] percentages){
		String browserPercentages = 
				"웹 브라우저 별 사용비울 " + System.lineSeparator() +  "IE : " + percentages[0] + "%" + System.lineSeparator() + "Firefox : " + percentages[1] + "%" + System.lineSeparator()
		+ "Safari : " + percentages[2] + "%" + System.lineSeparator() + "Chrome : " + percentages[3] + "%" + System.lineSeparator() + "Opera : " + percentages[4] + "%";
		return browserPercentages;
	}
	
	/*
	 *  input파일에서 각줄을 읽어서 위의 메서드들로 분석합니다.
	 *  
	 *  @return Analyzer reader input파일안의 정보를 가지고 있는 Analyzer을 리턴합니다.
	 */
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
		        }
		    s.close();
		return reader;	
	}
	
	/*
	 *  Analyzer의 정보를 output.log파일에 출렷하는 메소드입니다.
	 *  
	 *  @param Analyzer reader 파일의 정보를 읽은 Analyzer을 받습니다.
	 */
	public void fileOutput(Analyzer reader) throws IOException{
		
		String OUTPUT_FILE = "C:\\Users\\Administrator\\Downloads\\output.log";
		String maxAPIKeyString = reader.maxAPIKeyString(getTopValues(makeHashMap(toStringArray(reader.apiKeyList))));
		String eachStatusString = reader.eachStatusString();
		String topThreeAPIService = reader.topThreeAPIService(getTopValues(makeHashMap(toStringArray(reader.apiServiceIDList))));
		String peakTime = reader.peakTimeString(getTopValues(makeHashMap(toStringArray(reader.timeList))));
		String browserPercentageString = reader.browserPercentageString(getBrowserPercentage(reader.countIE, reader.countFirefox, reader.countSafari, reader.countChrome, reader.countOpera));
		
		//이미 파일이 있을시에 파일을 비우기 위해서 아래 두줄을 사용합니다.
		PrintWriter pw = new PrintWriter(OUTPUT_FILE);
		pw.close();		
		
		FileWriter fstream = new FileWriter(OUTPUT_FILE, true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(maxAPIKeyString);
        out.newLine();
        out.newLine();
        out.newLine();
        out.write(eachStatusString);
		out.newLine();
		out.newLine();
		out.newLine();
		out.write(topThreeAPIService);
		out.newLine();
		out.newLine();
		out.newLine();
		out.write(peakTime);
		out.newLine();
		out.newLine();
		out.newLine();
		out.write(browserPercentageString);
		
		out.close();
	}
	

	public static void main(String[] args) throws WrongInputException, IOException {
		Analyzer main = new Analyzer();
		main.fileOutput(main.reader());	
	}
}
