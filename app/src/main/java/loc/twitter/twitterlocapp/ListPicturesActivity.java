package loc.twitter.twitterlocapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import loc.twitter.twitterlocapp.adapters.TweetsAdapter;
import loc.twitter.twitterlocapp.beans.Tweet;
import loc.twitter.twitterlocapp.twitterservices.TwitterSearch;
import twitter4j.ExtendedMediaEntity;
import twitter4j.Status;

public class ListPicturesActivity extends AppCompatActivity
{
    private List<Tweet> tweetList;
    private RecyclerView recyclerView;
    private TweetsAdapter tweetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pictures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_pic_view);

        this.fetchTweets();
        tweetsAdapter = new TweetsAdapter(tweetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tweetsAdapter);

        tweetsAdapter.notifyDataSetChanged();
    }

    private void fetchTweets()
    {
        tweetList = new ArrayList<Tweet>();
        List<Status> tweets = TwitterSearch.getInstance().getSearchResults();
        ExtendedMediaEntity[] extendedMediaEntities;

        for (Status status : tweets)
        {
            // Not showing retweets
            if (status.isRetweet())
                continue;

            extendedMediaEntities = status.getExtendedMediaEntities();

            // A tweet might have multiple photos, we're showing only the first one.
            if (extendedMediaEntities != null && extendedMediaEntities.length>0)
                tweetList.add(new Tweet(status.getUser().getName(), status.getText(), extendedMediaEntities[0].getMediaURLHttps()));
        }
    }
}
