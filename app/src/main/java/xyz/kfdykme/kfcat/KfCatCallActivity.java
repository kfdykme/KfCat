package xyz.kfdykme.kfcat;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import com.google.gson.*;
import xyz.kfdykme.kfcat.data.*;
import java.util.*;
import com.orm.*;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.*;
import org.greenrobot.eventbus.*;
import xyz.kfdykme.kfcat.eventbus.*;
import com.orm.util.*;
import android.widget.Toast;


public class KfCatCallActivity extends AppCompatActivity {


	private FloatingActionButton fab;

	private SeekBar mSeekBarScale;
	private SeekBar mSeekBarDx;
	private SeekBar mSeekBarDy;

	private TextView mTextViewScale;
	private TextView mTextViewDx;
	private TextView mTextViewDy;

	private String Scale = "Scale";
	private String Dx = "Dx";
	private String Dy = "Dy";

	private KfCatSetting mSetting = new KfCatSetting();

	private Boolean isNotBye = false;

	private static final String TAG = "KfCatCallActivity";


	private void initView(){
		mSeekBarScale =  (SeekBar)findViewById(R.id.content_main_SeekBar_scale);
		mSeekBarDx =  (SeekBar)findViewById(R.id.content_main_SeekBar_dx);
		mSeekBarDy =  (SeekBar)findViewById(R.id.content_main_SeekBar_dy);

		mTextViewScale  = (TextView)findViewById(R.id.content_main_TextView_scale);
		mTextViewDx  = (TextView)findViewById(R.id.content_main_TextView_dx);
		mTextViewDy  = (TextView)findViewById(R.id.content_main_TextView_dy);

		if(SugarData.listAll(SugarData.class).isEmpty())
		{
			mSeekBarDx.setProgress(50);
			mSeekBarDy.setProgress(30);
			mSeekBarScale.setProgress(10);

		} else
		{
			KfCatData kfData = new Gson()
				.fromJson(SugarData.listAll(SugarData.class).get(0).cache,KfCatData.class);
			mSetting = kfData.getSetting();
			float x = mSetting.getDiatorX();
			float y = mSetting.getDiatorY();
			int ix = (int)x;
			int iy = (int)y;
			mSeekBarDx.setProgress(ix+50);
			mSeekBarDy.setProgress(iy+50);
			mSeekBarScale.setProgress(10);
		}


		mTextViewDy.setText(Dy +" : "+(mSeekBarDy.getProgress() -50));
		mTextViewDx.setText(Dx+" : "+(mSeekBarDx.getProgress() -50));
	//	mTextViewScale.setText(Scale +" : "+ mSeekBarScale.getProgress()/10.0);
//
//		mSeekBarScale.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
//
//				@Override
//				public void onStartTrackingTouch(SeekBar p1)
//				{
//					// TODO: Implement this method
//				}
//
//				@Override
//				public void onStopTrackingTouch(SeekBar p1)
//				{
//					// TODO: Implement this method
//				}
//
//
//				@Override
//				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
//				{
//					mTextViewScale.setText(Scale +" : "+ mSeekBarScale.getProgress()/10.0);
//				}
//			});

		mSeekBarDx.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}


				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					mSetting.setDiatorX(p2-50);

					mTextViewDx.setText(Dx+" : "+(mSeekBarDx.getProgress() -50));
				}
			});

		mSeekBarDy.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}


				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					mSetting.setDiatorY(p2-50);

					mTextViewDy.setText(Dy +" : "+(mSeekBarDy.getProgress() -50));
				}
			});



		fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					loadSetting();
					switchService();
					//EventBus.getDefault().postSticky(new Call2ServiceEvent(mSetting,isNotBye));

				}
			});
	}


	public void loadSetting(){
		//mSetting.setScale(mSeekBarScale.getProgress()/10.0f);

		//Log.i(TAG,"scale :" +mSetting.getScale());
		Log.i(TAG,"dx" +mSetting.getDiatorX());
		Log.i(TAG,"dy" +mSetting.getDiatorY());

	}


	public boolean isServiceWork(Context context, String serviceName){


		ActivityManager myAM = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);

		if(myList.size()  <= 0)
				return false;

		for(ActivityManager.RunningServiceInfo  info :myList){

			Log.i("isServiceWork",info.service.getClassName() +" == "+serviceName);
			if(info.service.getClassName().equals(serviceName))
			{
				return true;
			}
		}


		return false;
	}

	public void switchService(){
		Boolean isWork = isServiceWork(getApplicationContext(),"xyz.kfdykme.kfcat.KfCatService");
		Intent i = new Intent(getApplicationContext(),KfCatService.class);

		if(isWork){
			isNotBye = false;
			stopService(i);
		} else {
			saveData();
			startService(i);
			isNotBye = true;
		Snackbar.make(mSeekBarScale, "Click again to stop.", Snackbar.LENGTH_LONG)
			.setAction("Action", null).show();
		}
	}

	private void saveData(){
		KfCatData Data = new KfCatData(mSetting,isNotBye);
		SugarData sData ;
		if(SugarData.listAll(SugarData.class).isEmpty())
			sData = new SugarData(new Gson().toJson(Data,KfCatData.class));
		else
			{
				sData = SugarData.listAll(SugarData.class).get(0);
				sData.cache = new Gson().toJson(Data,KfCatData.class);
			}
		sData.save();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(R.color.colorPrimaryDark);

        setSupportActionBar(toolbar);

		SugarContext.init(this);


		initView();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_main, menu);
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
