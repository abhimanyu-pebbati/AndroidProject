package loc.twitter.twitterlocapp.controller;

import android.app.Activity;

/**
 * Created by Abhimanyu on 22-12-2017.
 */

public abstract class AbstractController
{
    private Activity activity;

    public AbstractController(Activity activity)
    {
        this.activity = activity;
    }

    public Activity getActivity()
    {
        return activity;
    }
}
