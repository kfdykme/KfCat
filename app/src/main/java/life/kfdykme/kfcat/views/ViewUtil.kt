package life.kfdykme.kfcat.views

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import xyz.kfdykme.kfcat.KfCatService
import xyz.kfdykme.kfcat.R
import xyz.kfdykme.kfcat.SugarData
import xyz.kfdykme.kfcat.data.KfCatData
import xyz.kfdykme.kfcat.data.KfCatSetting
import xyz.kfdykme.kfcat.view.ArcMenu

class ViewUtil {
    companion object {

        private var mSetting = KfCatSetting()

        private var isNotBye = false

        fun getArcMenu( context:Context): ArcMenu {
            val inflater = LayoutInflater.from(context)
            val arcMenu = inflater.inflate(R.layout.view_kfcat, null) as ArcMenu

            arcMenu.measure(
                View.MeasureSpec.makeMeasureSpec(
                    0, View.MeasureSpec.UNSPECIFIED
                ),
                View.MeasureSpec.makeMeasureSpec(
                    0, View.MeasureSpec.UNSPECIFIED
                )
            )

            return arcMenu
        }

        fun isServiceWork(context: Context, serviceName: String): Boolean {
            val myAM = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            val myList = myAM.getRunningServices(100)

            if (myList.size <= 0) return false

            for (info in myList) {
                Log.i("isServiceWork", info.service.className + " == " + serviceName)
                if (info.service.className == serviceName) {
                    return true
                }
            }


            return false
        }

        fun switchService(context: Context) {
            val isWork = isServiceWork(context, "xyz.kfdykme.kfcat.KfCatService")
            val i = Intent(context, KfCatService::class.java)

            if (isWork) {
                isNotBye = false
                context.stopService(i)
            } else {
                saveData()
                context.startForegroundService(i)
                isNotBye = true
//                Snackbar.make(binding.contentMainTextViewScale, "Click again to stop.", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            }
        }

        private fun saveData() {
            val Data = KfCatData(mSetting, isNotBye)
            val sData: SugarData
            if (SugarData.listAll(SugarData::class.java).isEmpty()) sData = SugarData(
                Gson().toJson(
                    Data,
                    KfCatData::class.java
                )
            )
            else {
                sData = SugarData.listAll(SugarData::class.java)[0];
                sData.cache = Gson().toJson(Data, KfCatData::class.java)
            }
            sData.save()
        }
    }
}