package xyz.kfdykme.kfcat.data;

public class KfCatData
{
	KfCatSetting setting;

	Boolean isNoBye;


	public KfCatData(KfCatSetting setting, Boolean isNoBye)
	{
		this.setting = setting;
		this.isNoBye = isNoBye;
	}




	public void setSetting(KfCatSetting setting)
	{
		this.setting = setting;
	}

	public KfCatSetting getSetting()
	{
		return setting;
	}

	public void setIsNoBye(Boolean isNoBye)
	{
		this.isNoBye = isNoBye;
	}

	public Boolean getIsNoBye()
	{
		return isNoBye;
	}}
