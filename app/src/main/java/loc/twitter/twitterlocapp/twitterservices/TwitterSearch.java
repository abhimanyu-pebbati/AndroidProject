package loc.twitter.twitterlocapp.twitterservices;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import twitter4j.ExtendedMediaEntity;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;

/**
 * Created by Abhimanyu on 23-12-2017.
 */

public class TwitterSearch
{
    private static TwitterSearch instance = null;
    private Twitter twitter;
    private List<Status> searchResults;

    private TwitterSearch()
    {
        this.twitter = TwitterLogin.getInstance().getTwitter();
    }

    public static TwitterSearch getInstance()
    {
        if (instance == null)
        {
            instance = new TwitterSearch();
        }

        return instance;
    }

    public List<Status> getSearchResults() {
        return searchResults;
    }

    public void searchTweets(String searchString, Location location, int radius)
    {
        Query query = new Query();
        query.setCount(101);
        query.setQuery(searchString);
        if (radius != 0)
        {
            GeoLocation geoLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
            query.setGeoCode(geoLocation, radius, Query.KILOMETERS);
        }

        QueryResult queryResult = null;
        try
        {
            queryResult = twitter.search(query);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (queryResult != null)
            searchResults = queryResult.getTweets();
    }
}
