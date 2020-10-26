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
    }

}