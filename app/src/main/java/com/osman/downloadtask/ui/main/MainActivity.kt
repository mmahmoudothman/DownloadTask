package com.osman.downloadtask.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.osman.downloadtask.R
import com.osman.downloadtask.adapter.ItemAdapter
import com.osman.downloadtask.data.VideoModel
import com.osman.downloadtask.databinding.ActivityMainBinding
import com.osman.downloadtask.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import zlc.season.rxdownload4.download
import zlc.season.rxdownload4.file

open class MainActivity : BaseActivity<MainViewModel>(), ItemAdapter.VideoSelectable {
    private lateinit var binding: ActivityMainBinding
    private var adapter: ItemAdapter? = null
    private lateinit var disposable: Disposable
    var tryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        adapter = ItemAdapter(viewModel!!.getBranchList(this) as ArrayList<VideoModel>, this)
        binding.rvFiles.adapter = adapter
    }

    override fun observeViewModel() {
        super.observeViewModel()
    }

    override fun videoSelected(videoModel: VideoModel, videoIndex: Int) {
        Toast.makeText(this, videoModel.name, Toast.LENGTH_LONG).show()
        if (videoModel.pathFileLocal == null) {
            videoModel.startDownload = true
            tryCount = 0
            downloadFile(videoModel, videoIndex)
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoModel.url))
            if (videoModel.type == "PDF")
                intent.setDataAndType(Uri.parse(videoModel.url), "application/pdf")
            else
                intent.setDataAndType(Uri.parse(videoModel.url), "video/mp4")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY;
            startActivity(intent)
        }
    }

    private fun downloadFile(videoModel: VideoModel, pos: Int) {
        disposable = videoModel.url.download()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { progress ->
                    Timber.e("${progress.downloadSizeStr()}/${progress.totalSizeStr()}")
                    videoModel.currentDownload = progress.downloadSize
                    videoModel.totalFileSize = progress.totalSize
                    videoModel.isCompleted = false
                    adapter?.updateItem(videoModel, pos)
                },
                onComplete = {
                    val file = videoModel.url.file()
                    videoModel.pathFileLocal = file.path
                    videoModel.startDownload = false
                    videoModel.isCompleted = true
                    adapter?.updateItem(videoModel, pos)
                },
                onError = {
                    videoModel.startDownload = false
                    videoModel.isCompleted = false
                    videoModel.isFailed = true
                    adapter?.updateItem(videoModel, pos)
                    while (tryCount < 3) {
                        Toast.makeText(this, "Retry$tryCount", Toast.LENGTH_LONG).show()
                        tryCount++
                        downloadFile(videoModel, pos)
                    }
                }
            )
    }
}