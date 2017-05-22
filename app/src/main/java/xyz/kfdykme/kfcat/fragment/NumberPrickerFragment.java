package xyz.kfdykme.kfcat.fragment;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import xyz.kfdykme.kfcat.R;
import android.widget.*;
import android.widget.NumberPicker.*;
import android.util.*;
import org.greenrobot.eventbus.*;

public class NumberPrickerFragment extends DialogFragment
{

	private static final String WEIGHT = " weight";

	private static final String HOUR = " hour";

	public int value = 0;

	public enum Mode {WEIGHT, HOUR};

	public Mode mode = Mode.WEIGHT;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_number_picker,null);
		NumberPicker numberPicker = (NumberPicker)view.findViewById(R.id.fragment_number_picker_NumberPicker);
		numberPicker.setMaxValue(50);
		numberPicker.setMinValue(0);
		numberPicker.setValue(value);
		getDialog().setCanceledOnTouchOutside(true);
		numberPicker.setOnValueChangedListener(new OnValueChangeListener(){

				@Override
				public void onValueChange(NumberPicker p1, int p2, int p3)
				{
					value = p3;
				}
			});

		TextView mTvValue = (TextView)view.findViewById(R.id.fragment_number_picker_TextView_value);

		if (mode.equals(Mode.WEIGHT))
			mTvValue.setText(WEIGHT);
		else
			mTvValue.setText(HOUR);

		TextView mTvSure = (TextView)view.findViewById(R.id.fragment_number_picker_TextView_sure);

		mTvSure.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					doSure();
				}


			});

		// TODO: Implement this method
		return view;
	}

	private void doSure()
	{
		getDialog().dismiss();
		EventBus.getDefault().post(new SureEvent(value,mode));
	}

	@Override
	public void onDestroy()
	{
		Log.i("NumberPicker",value+"");
		super.onDestroy();
	}

	public class SureEvent{

		public int value;

		public Mode mode;

		public SureEvent(int value, Mode mode){
			this.value = value;
			this.mode = mode;
		}
	}

}
