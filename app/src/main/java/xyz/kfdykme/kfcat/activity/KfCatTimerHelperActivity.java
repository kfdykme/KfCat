package xyz.kfdykme.kfcat.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import xyz.kfdykme.kfcat.*;
import android.support.v7.widget.Toolbar;
import com.orm.SugarContext;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.design.widget.*;
import android.view.View.*;
import android.view.*;
import xyz.kfdykme.kfcat.presenter.*;

public class KfCatTimerHelperActivity extends AppCompatActivity
{

	FloatingActionButton fab;

	TimeHelperPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timehelper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	//	toolbar.setTitleTextColor(R.color.colorPrimaryDark);
		toolbar.setTitle("KfCat - TimeHelper");
        setSupportActionBar(toolbar);



		SugarContext.init(this);

		mPresenter = new TimeHelperPresenterImpl(this);

		initView();
    }

	private void initView()
	{
		fab = (FloatingActionButton)findViewById(R.id.fab);

		fab.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					mPresenter.addTimeEvent();
				}
			});
	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_timehelper, menu);
        return true;
    }

	@Override
	protected void onDestroy()
	{
		SugarContext.terminate();
		super.onDestroy();
	}



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     	switch(item.getItemId()){
			case R.id.action_about:
				Toast.makeText(getApplicationContext(),"还没有写啦",Toast.LENGTH_SHORT).show();
				break;
			case R.id.action_help:

				Toast.makeText(getApplicationContext(),"还没有写啦",Toast.LENGTH_SHORT).show();

				break;
		}

        return super.onOptionsItemSelected(item);
    }


}
