package life.kfdykme.kfcat

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import life.kfdykme.kfcat.views.ViewUtil
import xyz.kfdykme.kfcat.KfCatService
import xyz.kfdykme.kfcat.R
import xyz.kfdykme.kfcat.SugarData
import xyz.kfdykme.kfcat.data.KfCatData
import xyz.kfdykme.kfcat.data.KfCatSetting
import xyz.kfdykme.kfcat.databinding.FragmentFirstBinding
import xyz.kfdykme.kfcat.view.KfCat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        activity?.let {
            _binding?.contentMain?.addView(View(it),200,200)
            val arcMenu = ViewUtil.getArcMenu(it);
            val kfCatView = arcMenu.findViewById<KfCat>(R.id.view_kfcat_ImageView)
            kfCatView.setAsRun();
            kfCatView.scaleX = 1.5f;
            kfCatView.scaleY = 1.5f;
            _binding?.contentMain?.addView(arcMenu, LinearLayout.LayoutParams(600, 600))
            _binding?.contentMain?.gravity = Gravity.CENTER_HORIZONTAL
        }

        initView();
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         context?.let {
             binding.root.setBackgroundColor(ContextCompat.getColor(it, R.color.colorPrimaryDark))
         }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private  val Dx = "Dx"
    private  val Dy = "Dy"

    private var mSetting = KfCatSetting()

    private var isNotBye = false


    private fun initView() {
        var mSeekBarDx = binding.contentMainSeekBarDx
        var mSeekBarDy = binding.contentMainSeekBarDy

        var mTextViewDx = binding.contentMainTextViewDx
        var mTextViewDy = binding.contentMainTextViewDy

        if (SugarData.listAll(SugarData::class.java).isEmpty()) {
            mSeekBarDx!!.progress = 50
            mSeekBarDy!!.progress = 30
        } else {
            val kfData = Gson()
                .fromJson(
                    SugarData.listAll(SugarData::class.java)[0].cache,
                    KfCatData::class.java
                )
            mSetting = kfData.setting
            val x = mSetting.diatorX
            val y = mSetting.diatorY
            val ix = x.toInt()
            val iy = y.toInt()
            mSeekBarDx!!.progress = ix + 50
            mSeekBarDy!!.progress = iy + 50
//            mSeekBarScale!!.progress = 10
        }


        mTextViewDy!!.text = Dy + " : " + (mSeekBarDy!!.progress - 50)
        mTextViewDx!!.text = Dx + " : " + (mSeekBarDx!!.progress - 50)

        mSeekBarDx!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p1: SeekBar) {
                // TODO: Implement this method
            }

            override fun onStopTrackingTouch(p1: SeekBar) {
                // TODO: Implement this method
            }


            override fun onProgressChanged(p1: SeekBar, p2: Int, p3: Boolean) {
                mSetting.diatorX = (p2 - 50).toFloat()

                mTextViewDx!!.text = Dx + " : " + (mSeekBarDx!!.progress - 50)
            }
        })

        mSeekBarDy!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p1: SeekBar) {
                // TODO: Implement this method
            }

            override fun onStopTrackingTouch(p1: SeekBar) {
                // TODO: Implement this method
            }


            override fun onProgressChanged(p1: SeekBar, p2: Int, p3: Boolean) {
                mSetting.diatorY = (p2 - 50).toFloat()

                mTextViewDy!!.text = Dy + " : " + (mSeekBarDy!!.progress - 50)
            }
        })



    }




}