package xyz.kfdykme.kfcat.view;
import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.drawable.AnimationDrawable;
import xyz.kfdykme.kfcat.R;

public class KfCat extends ImageView
{


	public KfCat(Context context){
		this(context,null);
	}

	public KfCat(Context context,AttributeSet attrs){
		this(context,attrs,0);
	}

	public KfCat(Context context,AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
	}


	public void reflashScaleX(){
		setScaleX(1);
	}

	public void setAsRun( ){

		AnimationDrawable anim = (AnimationDrawable)getResources().getDrawable(R.anim.run_animation);

		setImageDrawable(anim);

		anim.start();
	}

	public void setAsToumiao(Boolean isLeft){
		reflashScaleX();
		if(isLeft)
			setImageResource(R.drawable.image_toumiao1);
		else
			setImageResource(R.drawable.image_toumiao2);

	}

	public void setAsWuliao(){
		reflashScaleX();
		setImageResource(R.drawable.image_wuliao1);
	}

	public void setAsShufu(){
		AnimationDrawable anim = (AnimationDrawable)getResources().getDrawable(R.anim.shufu_animation);

		setImageDrawable(anim);
		anim.start();

//		 vAnim = ObjectAnimator.ofFloat(mImageView,"x",0,mImageView.getWidth()/2,0);
//		vAnim.setDuration(3000);
//		vAnim.setRepeatCount(ValueAnimator.INFINITE);
//		vAnim.setRepeatMode(ValueAnimator.REVERSE);
//		vAnim.start();
	}

}
