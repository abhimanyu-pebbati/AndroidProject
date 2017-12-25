package loc.twitter.twitterlocapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import loc.twitter.twitterlocapp.controller.HashSearchController;

public class HashSearchActivity extends AppCompatActivity
{

    HashSearchController controller = null;
    private static TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_search);

        initComponents();
    }

    private void initComponents()
    {
        controller = new HashSearchController(this);
        infoText = (TextView) findViewById(R.id.infoTextHashSearch);
    }

    public void searchClick(View view)
    {
        String hashSearchString = ((EditText) findViewById(R.id.searchForHashEditText)).getText().toString();
        int radius = 0;
        try
        {
            radius = Integer.parseInt(((EditText) findViewById(R.id.searchRadiusEditText)).getText().toString());
        } catch (Exception e) {}
        controller.searchClick(hashSearchString, radius);
    }

    public static void setInfoText(String s)
    {
        infoText.setText(s);
    }
}
