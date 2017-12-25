package loc.twitter.twitterlocapp.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import loc.twitter.twitterlocapp.HashSearchActivity;
import loc.twitter.twitterlocapp.ListPicturesActivity;
import loc.twitter.twitterlocapp.twitterservices.TwitterSearch;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Abhimanyu on 22-12-2017.
 */

public class HashSearchController extends AbstractController
{
    private static final int reqCode = 10;
    private static Location location = null;

    public HashSearchController(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                HashSearchController.location = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                this.getActivity().requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, HashSearchController.reqCode);
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
    }

    public void searchClick(String hashSearchString, int radius)
    {
        if (hashSearchString == null || "".equals(hashSearchString.trim()))
        {
            HashSearchActivity.setInfoText("Enter a valid search string to search for.");
            return;
        }

        if (location == null)
        {
            HashSearchActivity.setInfoText("Location service disabled or not permitted for the app. Enable location services and try again.");
            return;
        }

        if (radius != 0)
        {
            HashSearchActivity.setInfoText("Searching for: " + hashSearchString +
                    "\nLocation is: " +
                    "\nLatitude: " + location.getLatitude() +
                    "\nLongitude: " + location.getLongitude());
        }
        else
        {
            HashSearchActivity.setInfoText("Searching for: " + hashSearchString);
        }

        TwitterSearch twitterSearch = TwitterSearch.getInstance();
        twitterSearch.searchTweets(hashSearchString, location, radius);

        Intent listPicturePage = new Intent (this.getActivity(), ListPicturesActivity.class);
        this.getActivity().startActivity(listPicturePage);
    }
}
