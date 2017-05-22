package xyz.kfdykme.kfcat.presenter;
import xyz.kfdykme.kfcat.base.BasePresenter;
import xyz.kfdykme.kfcat.util.TimeEvent;

public interface TimeHelperPresenter extends BasePresenter
{
	void addTimeEvent();

	void editTimeEvent(TimeEvent e);

	void deleteTiemEvent(TimeEvent e);


}
