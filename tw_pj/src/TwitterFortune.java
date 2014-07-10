import java.io.BufferedReader;
import net.arnx.jsonic.JSON;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class TwitterFortune {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar date1 = Calendar.getInstance();
		int year = date1.get(Calendar.YEAR);
		int month = date1.get(Calendar.MONTH) + 1;
		int day = date1.get(Calendar.DATE);
		List<Seiza> fortuneResult = fortuneGet(year, month, day);
		for (Seiza seiza : fortuneResult){
			postTest(seiza, year, month, day);
		}
	}

	public static void postTest(Seiza result, int year, int month, int day) {
		Twitter twitter = TwitterFactory.getSingleton();
		String date = year + "/" + month + "/" + day;
		String tweetText = "【" + date + "の運勢】" + result.getSign() + " " + result.getRank() + "位\n";
		tweetText += " 総合運:" + getStar(result.getTotal());
		tweetText += "\n 恋愛運:" + getStar(result.getLove());
		tweetText += "\n 仕事運:" + getStar(result.getJob());
		tweetText += "\n 金　運:" + getStar(result.getMoney());
		tweetText += "\n" + result.getContent();
		if (tweetText.length() > 140){
			tweetText = tweetText.substring(0,138) + "…";
		}
		Status status = null;
		try {
			status = twitter.updateStatus(tweetText);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
//		System.out.println(status.toString());
	}
    private static String getStar(int number){
        String star = ""; 
    	switch(number){
    	case 1: star = "★☆☆☆☆"; break;
    	case 2: star = "★★☆☆☆"; break;
    	case 3: star = "★★★☆☆"; break;
    	case 4: star = "★★★★☆"; break;
    	case 5: star = "★★★★★"; break;
    	default: star = "☆☆☆☆☆"; break;
    	}
    	return star;
    }
	public static List<Seiza> fortuneGet(int year, int month, int day) {
// 結果をgetするURL作成
// http://api.jugemkey.jp/api/horoscope/free/2013/04/10
		String fortuneUrl = "http://api.jugemkey.jp/api/horoscope/free/";
		String strDate = String.format("%04d/%02d/%02d/", year, month, day);
		fortuneUrl += strDate;
//        GetContent(fortuneUrl);
        try {
            URL url = new URL(fortuneUrl);
            Object content = url.getContent();
//            if (content instanceof InputStream) {
            BufferedReader bf = new BufferedReader(new InputStreamReader( (InputStream)content) );        
            String line;
            line = bf.readLine();
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createJsonParser(line);
            List<Seiza> seizaList = new ArrayList<Seiza>(); 
            while (parser.nextToken() != JsonToken.END_ARRAY){
                Seiza seiza = new Seiza();
                while (parser.nextToken() != JsonToken.END_OBJECT){
                	String name = parser.getCurrentName();
                	if(name != null){
                        System.out.println(name);
                		parser.nextToken();
                		if(name.equals("content")){
                			seiza.setContent(parser.getText());
                		}else if(name.equals("item")){
                			seiza.setItem(parser.getText());
                		}else if(name.equals("money")){
                			seiza.setMoney(Integer.parseInt(parser.getText()));
                		}else if(name.equals("total")){
                			seiza.setTotal(Integer.parseInt(parser.getText()));
                		}else if(name.equals("job")){
                			seiza.setJob(Integer.parseInt(parser.getText()));
                		}else if(name.equals("color")){
                			seiza.setColor(parser.getText());
                		}else if(name.equals("love")){
                			seiza.setLove(Integer.parseInt(parser.getText()));
                		}else if(name.equals("rank")){
                			seiza.setRank(Integer.parseInt(parser.getText()));
                		}else if(name.equals("sign")){
                			seiza.setSign(parser.getText());
                		} 
                        System.out.println(seiza);
                	}
                }
            	seizaList.add(seiza);
            }
            System.out.println(seizaList.size());
            return seizaList;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("引数にURLを指定してください");
            System.exit(-1);
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
		
        return null;
	}
	
	class Fotune {
		private Seiza horoscope;

		public Seiza getHoroscope() {
			return horoscope;
		}

		public void setHoroscope(Seiza horoscope) {
			this.horoscope = horoscope;
		}
	}
}
