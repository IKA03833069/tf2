import java.io.BufferedReader;
import net.arnx.jsonic.JSON;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


public class TwitterFortune {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        System.out.println("test");
//		postTest();
		Fotune test = fortuneGet(2014, 3, 20);
		// TODO null返ってくるorz
		System.out.println(test.getHoroscope().getContent());

	}

	public static void postTest() {
		Twitter twitter = TwitterFactory.getSingleton();
		Status status = null;
		try {
			status = twitter.updateStatus("test post");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
//		System.out.println(status.toString());
	}
	public static Fotune fortuneGet(int year, int month, int day) {
//		http://api.jugemkey.jp/api/horoscope/2013/04/10
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
//                System.out.println(line);
                 // JSONをPOJOに変換します
                Fotune f = JSON.decode(line, Fotune.class);
                return f;
//            }
//            else {
//                System.out.println(content.toString());
//            }
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
	
	class Seiza {
		private String content;
		private String item;
		private Integer money;
		private Integer total;
		private Integer job;
		private String color;
		private Integer love;
		private Integer rank;
		private String sign;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		public Integer getMoney() {
			return money;
		}
		public void setMoney(Integer money) {
			this.money = money;
		}
		public Integer getTotal() {
			return total;
		}
		public void setTotal(Integer total) {
			this.total = total;
		}
		public Integer getJob() {
			return job;
		}
		public void setJob(Integer job) {
			this.job = job;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public Integer getLove() {
			return love;
		}
		public void setLove(Integer love) {
			this.love = love;
		}
		public Integer getRank() {
			return rank;
		}
		public void setRank(Integer rank) {
			this.rank = rank;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
	
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
