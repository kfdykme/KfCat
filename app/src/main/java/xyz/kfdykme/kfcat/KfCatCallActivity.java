package xyz.kfdykme.kfcat;

import android.os.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orm.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class KfCatCallActivity extends AppCompatActivity {


	private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//		toolbar.setTitleTextColor(R.color.colorPrimaryDark);

        setSupportActionBar(toolbar);

		SugarContext.init(this);


    }


	@Override
	protected void onDestroy()
	{
		SugarContext.terminate();
		super.onDestroy();
	}





}
