package com.xxh.ringtone.world.utils

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.xxh.ringtone.world.data.model.Song


class DownloadService(private val name: String ="DownloadService"): IntentService(name) {


    override fun onHandleIntent(intent: Intent?) {

        val song = intent!!.getParcelableExtra<Song>("song")!!
        Log.i("DownloadService","onHandleIntent..........${song.url}")

        val fileName = "${song.title}.mp3"
        download(this.baseContext,song.url,fileName)
    }

    private var downloadId: Long? = 0
    fun download(
        context: Context,
        downloadUrl: String,
        fileName: String,
    ): Long? {

        //创建下载任务，downloadUrl就是下载链接
        var request: DownloadManager.Request = DownloadManager.Request(Uri.parse(downloadUrl))

        request.apply {
            setTitle(fileName)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        }

        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_RINGTONES, fileName
        )

        //获取下载管理器
        var downloadManager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        //将下载任务加入下载队列，否则不会进行下载
        downloadId = downloadManager.enqueue(request)
        return downloadId
    }
}