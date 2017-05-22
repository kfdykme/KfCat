package xyz.kfdykme.kfcat.activity;


import android.os.*;
import android.support.v7.app.*;

import android.support.v7.widget.Toolbar;
import android.view.View.*;
import android.view.*;
import android.widget.*;

import xyz.kfdykme.kfcat.R;
import xyz.kfdykme.kfcat.fragment.*;
import android.content.*;
import org.greenrobot.eventbus.*;
import android.util.Log;
import com.orm.*;
import xyz.kfdykme.kfcat.util.*;



public class AddTimeEventActivity extends AppCompatActivity
{

	TextView mTVTime;

	TextView mTVWeight;

	Switch mSwitch;

	EditText mEtTitle;

	EditText mEtContent;

	int mTimeValue = 0;

	int mWeightValue= 1;

	public static final String TAG = "AddTimeEventActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_timeevent);

		//init SugarContext
		SugarContext.init(this);

		//init eventbus
		EventBus.getDefault().register(this);

		//init toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("");


        setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
		toolbar.setNavigationOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//save data
					if(!mEtContent.getText().toString().isEmpty() ||
					   !mEtTitle.getText().toString().isEmpty())
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
						builder.setTitle("Save this ?")
								.setNegativeButton("No", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
									// TODO: Implement this method
								}
							}).setPositiveButton("Yes", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
									saveTimeEvent();
								}

							}).show();
					}

					//finish();
				}
			});

		initView();
	}

	@Override
	protected void onDestroy()
	{

		//terminate
		SugarContext.terminate();
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onSureEvent(NumberPrickerFragment.SureEvent e){
		Log.i(TAG,"onSureEvent");
		switch(e.mode){
			case WEIGHT:
				mTVWeight.setText(e.value+" Weight");
				mWeightValue = e.value;
				break;
			case HOUR:
				mTVTime.setText(e.value+" Hour");
				mTimeValue = e.value;
				break;
		}
	}

	private void saveTimeEvent()
	{
		String title;
		if(mEtTitle.getText().toString().isEmpty())
			title = "unKnow";
		else
			title = mEtTitle.getText().toString();
		String content;
		if(mEtContent.getText().toString().isEmpty())
			content = "unKnow";
		else
			content= mEtContent.getText().toString();

		NumberPrickerFragment.Mode mode;
		int value;
		if(mSwitch.isChecked())
		{
			//weight
			mode = NumberPrickerFragment.Mode.WEIGHT;
			value = mWeightValue;
		} else {
			//hour
			mode = NumberPrickerFragment.Mode.HOUR;
			value = mTimeValue;
		}
		new TimeEvent(title,content,mode,value).save();

	}


	private void initView()
	{
		mTVWeight = (TextView)findViewById(R.id.activity_add_timeevent_TextView_weight);
		mTVTime = (TextView)findViewById(R.id.activity_add_timeevent_TextView_time);

		mSwitch = (Switch)findViewById(R.id.activity_add_timeevent_Switch);

		changeSwitch();

		mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					changeSwitch();
				}
			});


		mTVWeight.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					NumberPrickerFragment nF = new NumberPrickerFragment();
					nF.show(getSupportFragmentManager(),"Weight Picker");
					nF.mode = NumberPrickerFragment.Mode.WEIGHT;
					nF.value = mWeightValue;
				}
			});

		mTVTime.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					NumberPrickerFragment nF = new NumberPrickerFragment();
					nF.show(getSupportFragmentManager(),"Hour Picker");
					nF.mode = NumberPrickerFragment.Mode.HOUR;
					nF.value = mTimeValue;
				}
			});
		mTVTime.setText(mTimeValue + " Hour");
		mTVWeight.setText(mWeightValue+ " Weight");

		mEtContent = (EditText)findViewById(R.id.activity_add_timeevent_EditText_content);
		mEtTitle = (EditText)findViewById(R.id.activity_add_timeevent_EditText_title);

	}

	private void changeSwitch()
	{
		if(mSwitch.isChecked())
		{
			mTVWeight.setTextColor(getResources().getColor(R.color.colorAccent));
			mTVTime.setTextColor(getResources().getColor(R.color.colorPrimary));
		} else {
			mTVWeight.setTextColor(getResources().getColor(R.color.colorPrimary));
			mTVTime.setTextColor(getResources().getColor(R.color.colorAccent));
		}
	}



}
