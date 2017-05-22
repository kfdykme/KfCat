package xyz.kfdykme.kfcat.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.View;
import android.util.Log;
import android.util.TypedValue;
import android.content.res.TypedArray;
import xyz.kfdykme.kfcat.R;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.animation.AnimationSet;
import android.view.animation.Animation;
import android.view.animation.*;

public class ArcMenu extends ViewGroup {


	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	public void setCurrentStatus(Status mCurrentStatus)
	{
		this.mCurrentStatus = mCurrentStatus;
	}

	public Status getCurrentStatus()
	{
		return mCurrentStatus;
	}

	public void setPosition(Position mPosition)
	{
		this.mPosition = mPosition;
	}

	public Position getPosition()
	{
		return mPosition;
	}



	public enum Position
	{
		LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
	}

	public enum Status
	{
		OPEN,CLOSE
	}


	//
	public interface OnMenuItemClickListener{
		void onClick(View view,int pos);
	}

	public static final String TAG = "ArcMenu";

	private Position mPosition = Position.LEFT_TOP;

	private int mRadius;

	private Status mCurrentStatus = Status.CLOSE;

	private View mCButton;

	private OnMenuItemClickListener mMenuItemClickListener;

	public void setOnMenuItemClickListener( OnMenuItemClickListener mMenuItemClickListener)
	{
		this.mMenuItemClickListener = mMenuItemClickListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int count = getChildCount();
		for (int i =0; i < count; i++)
		{
			measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
		}

		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
	}



	@Override
	protected void onLayout(boolean changed, int p2, int p3, int p4, int p5)
	{
		if(changed)
		{
			layoutCButton();

			int count = getChildCount();

			for (int i =0; i < count -1; i++)
			{
				View child = getChildAt(i+1);

				child.setVisibility(View.GONE);

				int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count -2) * i));
				int ct  =(int )(mRadius * Math.cos(Math.PI/2/ (count -2) * i));

				int cWidth = child.getMeasuredWidth();
				int cHeight = child.getMeasuredHeight();
				if(mPosition ==Position.LEFT_BOTTOM
				 || mPosition == Position.RIGHT_BOTTOM){
					 ct = getMeasuredHeight() - cHeight -ct;
				 }

				if(mPosition ==Position.RIGHT_TOP
				   || mPosition == Position.RIGHT_BOTTOM){
					cl= getMeasuredWidth() - cWidth-cl;
				}

				child.layout(cl,ct,cl+cWidth,ct+cHeight);
			}
		}
	}

	private void layoutCButton()
	{
		mCButton = getChildAt(0);

		mCButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//open menu

					toggleMenu(300);
				}

			});

		int l = 0;

		int t = 0;

		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();

		switch(mPosition){
			case LEFT_TOP:
				l =0;
				t = 0;
				break;
			case LEFT_BOTTOM:
				l = 0;
				t = getMeasuredHeight() - height;
				break;
			case RIGHT_TOP:
				l = getMeasuredWidth() - width;
				t = 0;
				break;
			case RIGHT_BOTTOM:
				l = getMeasuredWidth() - width;
				t = getMeasuredHeight() - height;

				break;
			default:
				break;
		}

		mCButton.layout(l,t,l+width,t+height);
	}


	private void toggleMenu(int duration)
	{
		//add anim to menuItem
		int count = getChildCount();

		for (int i =1; i< count ;i++)
		{
			final View child = getChildAt(i);

			child.setVisibility(View.VISIBLE);

			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count -2) * i));
			int ct  =(int )(mRadius * Math.cos(Math.PI/2/ (count -2) * i));

			int xflag =1;
			int yflag = 1;


		 	if(mPosition ==Position.LEFT_BOTTOM
			   || mPosition == Position.LEFT_TOP){
				   xflag = -1;
			}

			if(mPosition ==Position.RIGHT_TOP
			   || mPosition == Position.LEFT_TOP){
				   yflag = -1;
			}

			AnimationSet animset = new AnimationSet(true);

			Animation tranAnim  =null;

			if(mCurrentStatus == Status.CLOSE)
			{
				tranAnim = new TranslateAnimation(xflag * cl,0,yflag * ct,0);
				child.setClickable(true);
				child.setFocusable(true);
			} else {
				tranAnim = new TranslateAnimation(0,xflag * cl,0,yflag * ct);
				child.setClickable(false);
				child.setFocusable(false);

			}

			tranAnim.setFillAfter(true);
			tranAnim.setDuration(duration);
			tranAnim.setStartOffset(i*100/count);


			tranAnim.setAnimationListener(new Animation.AnimationListener(){

					@Override
					public void onAnimationStart(Animation p1)
					{
					}

					@Override
					public void onAnimationEnd(Animation p1)
					{
							if(mCurrentStatus == Status.CLOSE){
								child.setVisibility(View.GONE);
							}
					}

					@Override
					public void onAnimationRepeat(Animation p1)
					{
					}
				});

			RotateAnimation rotateAnim = new RotateAnimation(
			0
			,720
			,Animation.RELATIVE_TO_SELF
			,0.5f
			,Animation.RELATIVE_TO_SELF
			,0.5f);

			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);

			animset.addAnimation(rotateAnim);
			animset.addAnimation(tranAnim);

			child.startAnimation(animset);


		}
		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN:Status.CLOSE);

	}

	public ArcMenu(Context context){
		this(context,null);
	}

	public ArcMenu(Context context,AttributeSet attrs){
		this(context,attrs,0);
	}

	public ArcMenu(Context context, AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		//defalut mRadius
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
		,100
		,getResources().getDisplayMetrics());
		//get

		TypedArray a = context.getTheme().obtainStyledAttributes(
			attrs
			,R.styleable.ArcMenu
			,defStyle
			,0);

		mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius
			,mRadius);

		Log.e(TAG,"Raiuse " +mRadius);

		a.recycle();

		//
		int pos = POS_LEFT_BOTTOM;
		switch(pos){
			case POS_LEFT_TOP:
				mPosition = Position.LEFT_TOP;
				break;
			case POS_LEFT_BOTTOM:
				mPosition = Position.LEFT_BOTTOM;
				break;
			case POS_RIGHT_TOP:
				mPosition = Position.RIGHT_TOP;
				break;
			case POS_RIGHT_BOTTOM:
				mPosition = Position.RIGHT_BOTTOM;
				break;

		}



	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return false || super.dispatchTouchEvent(ev);
	}


}
