package loc.twitter.twitterlocapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import loc.twitter.twitterlocapp.R;
import loc.twitter.twitterlocapp.beans.Tweet;

/**
 * Created by Abhimanyu on 24-12-2017.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.MyViewHolder>
{
    private List<Tweet> tweetsList = new ArrayList<Tweet>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, tweetText;
        public ImageView tweetImage;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userNameID);
            tweetText = view.findViewById(R.id.tweetTextID);
            tweetImage = view.findViewById(R.id.tweetImageID);
        }
    }


    public TweetsAdapter(List<Tweet> tweetsList) {
        this.tweetsList = tweetsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tweet tweet = tweetsList.get(position);
        holder.userName.setText(tweet.getUserName());
        holder.tweetText.setText(tweet.getTweetText());
        FetchImageFromURL imgURL = new FetchImageFromURL(holder.tweetImage);
        imgURL.execute(tweet.getUrl());
    }

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }

    private class FetchImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public FetchImageFromURL(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
