package com.xxh.ringtone.world.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException

class Utils {

    companion object {
        val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100

        fun check(activity: Activity) {

            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE
                )
            }
        }

        fun checkPermission(activity: Activity): Boolean {

            return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }

        /**
         * 创建 文件夹
         * @param dirPath 文件夹路径
         * @return 结果码
         */
        fun createDir(dirPath: String) {
            val file = File(dirPath)
            if (!file.exists()) {
                file.mkdirs()
            }
        }

        private var db: SQLiteDatabase? = null
        private var db_file: File?  = null

        fun exist(context: Context, dbName: String): Boolean{
            var flag: Boolean = false
            try {
                db_file = context.getDatabasePath(dbName)
                flag = db_file!!.exists()
            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
            return flag
        }

        private fun milliSecondsToTimer(milliseconds: Long): String? {
            var finalTimerString = ""
            var secondsString = ""

            // Convert total duration into time
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
            // Add hours if there
            if (hours > 0) {
                finalTimerString = "$hours:"
            }

            // Prepending 0 to seconds if it is one digit
            secondsString = if (seconds < 10) {
                "0$seconds"
            } else {
                "" + seconds
            }
            finalTimerString = "$finalTimerString$minutes:$secondsString"

            // return timer string
            return finalTimerString
        }

        fun getProgressPercentage(currentDuration: Long, totalDuration: Long): Int {
            val percentage: Double
            val currentSeconds: Long = (currentDuration / 1000)
            val totalSeconds: Long = (totalDuration / 1000)
            percentage = currentSeconds.toDouble() / totalSeconds * 100
            return percentage.toInt()
        }
    }

}