package xyz.kfdykme.kfcat;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import android.annotation.*;
import android.graphics.drawable.*;
import android.animation.*;
import xyz.kfdykme.kfcat.data.KfCatSetting;
import java.util.List;
import com.google.gson.*;
import com.orm.*;
import android.util.*;
import org.greenrobot.eventbus.*;
import xyz.kfdykme.kfcat.eventbus.*;
import android.view.View.*;
import xyz.kfdykme.kfcat.data.KfCatData;
import android.support.design.widget.*;
import xyz.kfdykme.kfcat.view.KfCat;
import xyz.kfdykme.kfcat.view.ArcMenu;

public class KfCatService extends Service
{

	ArcMenu mView;

	KfCat mKfCat;

	WindowManager mWindowManager;

	WindowManager.LayoutParams wmParams;

	ValueAnimator vAnim;

	boolean isLongClick = false;

	boolean isNotBye = true;

	KfCatSetting mSetting;

	private static final String TAG = "KfCatService";

	public  final String serviceName = "xyz.kfdykme.kfcat.kfcat.KfCatService";



	private void createKfCatView()
	{
		wmParams = new WindowManager.LayoutParams();

		mWindowManager  = (WindowManager) getApplication()
			.getSystemService(
			WINDOW_SERVICE);

		wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		wmParams.gravity = Gravity.LEFT|Gravity.TOP;
		wmParams.x  =0;
		wmParams.y = 0;
		LayoutInflater inflater = LayoutInflater.from(getApplication());

		mView = (ArcMenu) inflater.inflate(R.layout.view_kfcat,null);

        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY; // 高版本专用类型;
        // 其他参数（如宽度、高度、位置等）
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ;
		wmParams.format = PixelFormat.TRANSLUCENT;

		mKfCat = (KfCat)mView.findViewById(R.id.view_kfcat_ImageView);

		mWindowManager.addView(mView,wmParams);

		mView.measure(View.MeasureSpec.makeMeasureSpec(
						  0,View.MeasureSpec.UNSPECIFIED)
					  ,View.MeasureSpec.makeMeasureSpec(
						  0,View.MeasureSpec.UNSPECIFIED));




	}

	private void setListener()
	{

		mKfCat.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Toast.makeText(getApplicationContext(),"Miao",Toast.LENGTH_SHORT).show();

					//Snackbar.make(mImageView,"Miao",Snackbar.LENGTH_SHORT).show();
				}
			});

		mKfCat.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)
				{
					isLongClick = true;

					mKfCat.setAsRun();
					return false;
				}
			});

		mKfCat.setOnTouchListener(new OnTouchListener(){


				int dx = (int)(mSetting.getDiatorX() * 10);
				int dy = (int) mSetting.getDiatorY() * 10;

				//init lastX
				float lastX = 0;
				@Override
				public boolean onTouch(View p1, MotionEvent e)
				{

					if (isLongClick)
						toRun(e);

					if (e.getAction() == MotionEvent.ACTION_UP)
					{
						isLongClick = false;

						//
						toTouMiao();

					}
					if(mView.getCurrentStatus() == ArcMenu.Status.CLOSE){
						wmParams.width = mKfCat.getWidth();
						wmParams.height = mKfCat.getHeight();
					} else {
						wmParams.width = mView.getHeight() + mView.getChildAt(1).getHeight();
						wmParams.height = wmParams.width;
					}

					mWindowManager.updateViewLayout(mView, wmParams);

					lastX = e.getRawX();
					return false;
				}

				private void toRun(MotionEvent e)
				{
					if (lastX < e.getRawX())
					{
						mKfCat.setScaleX(-1);
					}
					else
					{
						mKfCat.setScaleX(1);
					}

					wmParams.x = (int)(e.getRawX() + dx);
					wmParams.y = (int) (e.getRawY() + dy);

					int halfWidth = mWindowManager.getDefaultDisplay().getWidth()/2;
					int halfHeight = mWindowManager.getDefaultDisplay().getHeight()/2;

					if(wmParams.x >= halfWidth && wmParams.y <halfHeight)
					{
						mView.setPosition(ArcMenu.Position.RIGHT_TOP);
					} else if(wmParams.x >= halfWidth && wmParams.y >= halfHeight)
					{
						mView.setPosition(ArcMenu.Position.RIGHT_BOTTOM);
					} else if(wmParams.x < halfWidth && wmParams.y >= halfHeight)
					{
						mView.setPosition(ArcMenu.Position.LEFT_BOTTOM);
					}  else if(wmParams.x < halfWidth && wmParams.y <halfHeight)
					{
						mView.setPosition(ArcMenu.Position.LEFT_TOP);
					}

					Log.i(TAG, "dx" + dx);
					Log.i(TAG, "dy" + dy);
				}

				private void toTouMiao()
				{
					if (wmParams.x + mKfCat.getWidth() / 2 < 100)
					{
						mKfCat.setAsToumiao(true);
						wmParams.x = 0;
					}
					else if (wmParams.x + mKfCat.getWidth() / 2 > mWindowManager.getDefaultDisplay().getWidth() - 100)
					{
						mKfCat.setAsToumiao(false);
						wmParams.x = mWindowManager.getDefaultDisplay().getWidth();
					}
					else
						mKfCat.setAsWuliao();
				}
			});


	}


//
//	@Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
//	public void onLoad(Call2ServiceEvent e){
//		isNotBye = e.getIsNotBye();
//		mSetting = e.getSetting();
//
//
//	}
//



	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onCreate()
	{

		super.onCreate();
		SugarContext.init(this);
		loadData();

		createKfCatView();

		setListener();

	//	EventBus.getDefault().register(this);
	}

	private void loadData()
	{
		KfCatData data ;
		List<SugarData> sDatas = SugarData.listAll(SugarData.class);
		data = new Gson().fromJson(sDatas.get(0).cache,KfCatData.class);

		Log.i("Sugar",sDatas.get(0).cache);

		mSetting = data.getSetting();
		isNotBye = data.getIsNoBye();
	}






	@Override
	public void onDestroy()
	{
		if(mView != null)
		{

			mWindowManager.removeViewImmediate(mView);

		}

		if(isNotBye){
			Intent i = new Intent(getApplicationContext(),KfCatService.class);

			startService(i);
		}
		stopForeground(true);

		super.onDestroy();

	}

    @Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

		String channelId = "life.kfdykme.kcat.service";
		String channelName = "KfCat 服务通道"; // 渠道名称（用户可见）
		int importance = NotificationManager.IMPORTANCE_LOW; // 重要性（决定通知是否弹出）

		NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
		// 可选配置（根据需求调整）
		channel.setDescription("KfCat 后台服务状态通知"); // 渠道描述（用户可见）
		channel.enableLights(false); // 关闭呼吸灯
		channel.setSound(null, null); // 关闭通知声音
		channel.enableVibration(false); // 关闭震动
		// 获取系统通知管理器并创建渠道
		NotificationManager notificationManager = getSystemService(NotificationManager.class);
		notificationManager.createNotificationChannel(channel);
		PendingIntent pendingintent = PendingIntent.getActivity(this
				,0
				,new Intent(this,KfCatCallActivity.class)
				,FLAG_IMMUTABLE);
		Notification.Builder builder = new Notification.Builder(getApplicationContext(), channelId);

		builder.setSmallIcon(R.drawable.image_launcher)
			.setContentTitle("KfCat")
			.setWhen(System.currentTimeMillis())

			.setContentIntent(pendingintent)
			.setContentText("Hello..")
			;

		startForeground(0x111,builder.build());

		return START_STICKY;
	}





}
