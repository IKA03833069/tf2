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
		postTest();
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
}
