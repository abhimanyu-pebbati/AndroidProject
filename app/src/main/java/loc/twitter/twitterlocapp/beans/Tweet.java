package loc.twitter.twitterlocapp.beans;

/**
 * Created by Abhimanyu on 24-12-2017.
 */

public class Tweet
{
    private static final String USER_NAME = "Tweet by: ";
    private static final String TWEET = "Tweet text: ";

    private String userName;
    private String tweetText;
    private String url;

    public Tweet(String userName, String tweetText, String url) {
        this.userName = USER_NAME + userName;
        this.tweetText = TWEET + tweetText;
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public String getTweetText() {
        return tweetText;
    }

    public String getUrl() {
        return url;
    }

}
