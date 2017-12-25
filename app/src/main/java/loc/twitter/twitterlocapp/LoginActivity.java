package loc.twitter.twitterlocapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import loc.twitter.twitterlocapp.controller.LoginController;

public class LoginActivity extends AppCompatActivity {

    private LoginController controller;
    private static TextView infoText;

    public void initComponents()
    {
        controller = new LoginController(this);
        infoText = (TextView) findViewById(R.id.infoTextLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT > 14) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        initComponents();
    }

    public void loginClick(View view)
    {
        setInfoText("");
        controller.loginClick();
    }

    public void authorizeClick(View view)
    {
        setInfoText("");
        String pin = ((EditText) findViewById(R.id.authPinEditText)).getText().toString();
        controller.authorizeClick(pin);
    }

    public static void setInfoText(String s)
    {
        infoText.setText(s);
    }
}
