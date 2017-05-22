package xyz.kfdykme.kfcat.eventbus;
import xyz.kfdykme.kfcat.data.*;

public class Call2ServiceEvent
{
	KfCatSetting setting;
	Boolean isNotBye;

	public Call2ServiceEvent(KfCatSetting setting, Boolean isNotBye)
	{
		this.setting = setting;
		this.isNotBye = isNotBye;
	}



	public void setSetting(KfCatSetting setting)
	{
		this.setting = setting;
	}

	public KfCatSetting getSetting()
	{
		return setting;
	}

	public void setIsNotBye(Boolean isNotBye)
	{
		this.isNotBye = isNotBye;
	}

	public Boolean getIsNotBye()
	{
		return isNotBye;
	}}
