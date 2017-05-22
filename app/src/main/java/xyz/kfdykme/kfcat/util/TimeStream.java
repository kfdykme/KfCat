package xyz.kfdykme.kfcat.util;

public class TimeStream
{
	TimeUnit mStart;
	TimeUnit mEnd;


	public TimeStream(TimeUnit mStart, TimeUnit mEnd)
	{
		this.mStart = mStart;
		this.mEnd = mEnd;
	}



	public void setMStart(TimeUnit mStart)
	{
		this.mStart = mStart;
	}

	public TimeUnit getMStart()
	{
		return mStart;
	}

	public void setMEnd(TimeUnit mEnd)
	{
		this.mEnd = mEnd;
	}

	public TimeUnit getMEnd()
	{
		return mEnd;
	}}
