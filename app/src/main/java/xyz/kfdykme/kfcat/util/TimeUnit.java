package xyz.kfdykme.kfcat.util;

public class TimeUnit
{
	private enum Mode{WEIGHT,TIME};

	private static final Mode DEFAULT_MODE = Mode.WEIGHT;

	private static final int DEFAULT_WEIGHT = 1;

	private int mWeight = DEFAULT_WEIGHT;

	private int mHour;

	private int mMinute;

	private int mSecond;

	private Mode mMode = DEFAULT_MODE;

	public TimeUnit(){

	}

	public void setMWeight(int mWeight)
	{
		this.mWeight = mWeight;
	}

	public int getMWeight()
	{
		return mWeight;
	}

	public void setMHour(int mHour)
	{
		this.mHour = mHour;
	}

	public int getMHour()
	{
		return mHour;
	}

	public void setMMinute(int mMinute)
	{
		this.mMinute = mMinute;
	}

	public int getMMinute()
	{
		return mMinute;
	}

	public void setMSecond(int mSecond)
	{
		this.mSecond = mSecond;
	}

	public int getMSecond()
	{
		return mSecond;
	}

	public void setMMode(Mode mMode)
	{
		this.mMode = mMode;
	}

	public Mode getMMode()
	{
		return mMode;
	}


}
