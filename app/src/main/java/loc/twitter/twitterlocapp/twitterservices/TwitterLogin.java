package loc.twitter.twitterlocapp.twitterservices;

import loc.twitter.twitterlocapp.utils.Constants;
import loc.twitter.twitterlocapp.utils.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Abhimanyu on 21-12-2017.
 */

public class TwitterLogin
{
    private static TwitterLogin instance;
    private Twitter twitter = null;
    private RequestToken requestToken = null;
    private AccessToken accessToken = null;

    private TwitterLogin()
    {
    }

    public static TwitterLogin getInstance()
    {
        if (instance == null)
        {
            instance = new TwitterLogin();
        }

        return instance;
    }

    public AccessToken getAccessToken()
    {
        return accessToken;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public String getAuthenticationURL()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Constants.CONSUMER_KEY)
                .setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
                //.setOAuthAccessToken(Constants.ACCESS_TOKEN)
                //.setOAuthAccessTokenSecret(Constants.ACCESS_TOKEN_SECRET);

        try
        {
            TwitterFactory twitterFactory = new TwitterFactory(cb.build());
            twitter = twitterFactory.getInstance();

            try
            {
                requestToken = twitter.getOAuthRequestToken();
                Logger.log("Req token = " + requestToken.getToken());
                Logger.log("Req token secret= " + requestToken.getTokenSecret());
                Logger.log("Authentication URL= " + requestToken.getAuthenticationURL());
                Logger.log("Authorization URL= " + requestToken.getAuthorizationURL());

                return requestToken.getAuthenticationURL();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean authenticatePin(String pin)
    {
        if (twitter == null) {
            Logger.log("Fetch request token first before authentication.");
            return false;
        }

        try
        {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            Logger.log("Access Token= " + accessToken.getToken());
            Logger.log("Access Token Secret= " + accessToken.getTokenSecret());

            return true;
        }
        catch (Exception e)
        {
            Logger.log("Authentication of PIN failed.");
        }

        return false;
    }
}
