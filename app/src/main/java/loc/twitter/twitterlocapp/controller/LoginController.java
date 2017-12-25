package loc.twitter.twitterlocapp.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import loc.twitter.twitterlocapp.HashSearchActivity;
import loc.twitter.twitterlocapp.LoginActivity;
import loc.twitter.twitterlocapp.twitterservices.TwitterLogin;
import loc.twitter.twitterlocapp.utils.Logger;

/**
 * Created by Abhimanyu on 22-12-2017.
 */

public class LoginController extends AbstractController
{
    public LoginController(Activity activity)
    {
        super(activity);
    }

    public void loginClick()
    {
        String authURL = TwitterLogin.getInstance().getAuthenticationURL();
        Logger.log("Authorization URL: " + authURL);
        this.redirectURL(authURL);
    }

    public void authorizeClick(String pin)
    {
        if (TwitterLogin.getInstance().getAccessToken() != null)
        {
            openHashSearchPage();
            return;
        }

        if(!TwitterLogin.getInstance().authenticatePin(pin))
        {
            LoginActivity.setInfoText("Pin authentication failed.\nHit 'Sign in' to authorize again and enter a valid pin.");
        }
        else
        {
            LoginActivity.setInfoText("Login success");
            openHashSearchPage();
        }
    }

    private void openHashSearchPage()
    {
        Intent hashSearchPage = new Intent (this.getActivity(), HashSearchActivity.class);
        this.getActivity().startActivity(hashSearchPage);
    }

    private void redirectURL(String URL)
    {
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(URL));
        this.getActivity().startActivity(viewIntent);
    }
}
