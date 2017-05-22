package xyz.kfdykme.kfcat.presenter;
import xyz.kfdykme.kfcat.util.TimeEvent;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import xyz.kfdykme.kfcat.activity.*;

public class TimeHelperPresenterImpl implements TimeHelperPresenter
{

	Context context;

	private static final String TAG = "TimeHelperPresenterImpl";

	public TimeHelperPresenterImpl(Context context)
	{
		this.context = context;
	}


	@Override
	public void addTimeEvent()
	{
		Log.i(TAG,"addTimeEvent");

		Intent i = new Intent(context,AddTimeEventActivity.class);

		context.startActivity(i);
	}

	@Override
	public void editTimeEvent(TimeEvent e)
	{

	}

	@Override
	public void deleteTiemEvent(TimeEvent e)
	{
		// TODO: Implement this method
	}

}
