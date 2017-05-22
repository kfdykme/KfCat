package xyz.kfdykme.kfcat.util;
import xyz.kfdykme.kfcat.fragment.*;
import com.orm.*;

public class TimeEvent extends SugarRecord
{
	private String title;

	private String content;

	//private TimeStream timeStream;

	private static final String WEIGHT ="weight";

	private static final String LENGHT = "length";

	private String mode;

	private int value;

	public TimeEvent(String title,String content, NumberPrickerFragment.Mode mode, int vuale){
		this.title = title;
		this.content = content;

		switch(mode){
			case WEIGHT:
				this.mode = WEIGHT;
				break;
			case HOUR:
				this.mode = LENGHT;
				break;
		}

		this.value = value;
	}





}
