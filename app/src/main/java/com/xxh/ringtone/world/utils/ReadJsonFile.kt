package com.xxh.ringtone.world.utils

import android.app.Activity
import android.content.res.AssetManager
import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.xxh.ringtone.world.data.model.Song
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class ReadJsonFile {


    companion object {
        val ringtoneFileName = "ringtone.json"

        private fun getJson(activity: Activity, fileName: String): String {

            val stringBuilder = StringBuilder()
            try {
                val assetManager: AssetManager = activity.assets
                val bf = BufferedReader(
                    InputStreamReader(
                        assetManager.open(fileName)
                    )
                )
                var line: String?
                while (bf.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

        private fun getJsonArray(json: String?): JSONArray {
            return JSONArray(json)
        }

        /**
         * 将字符串转换为 对象
         * @param json
         * @param type
         * @return
         */
        private fun <T> jsonToObject(json: String?, type: Class<T>?): T {
            val gson = Gson()
            return gson.fromJson(json, type)
        }

        private fun getRingtoneListString(activity: Activity): String {
            return getJson(activity, ringtoneFileName)
        }

        fun getRingtoneList(activity: Activity): MutableList<Song> {
            var ringtoneArray = mutableListOf<Song>()
            val str = getRingtoneListString(activity)
            val jsonArray = getJsonArray(str)
            val len = jsonArray.length() - 1
            for (i in 0..len) {
                var jsonObject = jsonArray.getJSONObject(i)
                var song = jsonToObject(jsonObject.toString(), Song::class.java)

                ringtoneArray.add(song)
            }
            return ringtoneArray
        }

        class MyTaskParams(val context: Activity, val fileName: String)


    }


}