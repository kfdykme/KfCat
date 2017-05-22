package xyz.kfdykme.kfcat.activity;
import android.support.v7.app.*;
import android.os.*;
import android.support.v7.widget.*;
import xyz.kfdykme.kfcat.*;

public class KfCatSettingsActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



}
