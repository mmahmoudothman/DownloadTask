package com.osman.downloadtask.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.osman.downloadtask.R

open class BaseActivity<T : BaseActivityViewModel?> : AppCompatActivity() {
    private var loadingDialog: Dialog? = null
    var viewModel: T? = null

    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
    }

    protected open fun observeViewModel() {
        viewModel?.getShowLoadingProgress()?.observe(this,
            { showLoadingProgress: Boolean -> if (showLoadingProgress) showLoadingProgress() else hideLoadingProgress() })
        viewModel?.getFailMessage()?.observe(this,
            { failMessage: String -> showFailMessage(failMessage) })
    }

    open fun showLoadingProgress() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(this)
            loadingDialog!!.setCancelable(false)
            loadingDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog!!.setContentView(R.layout.dialog_loading)
        }
        if (!loadingDialog!!.isShowing && !this.isFinishing) loadingDialog!!.show()
    }

    open fun hideLoadingProgress() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    open fun showFailMessage(message: String?) {
        if (message == null) return
        viewModel!!.setShowLoadingProgress(false)
        val dialog: SweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.dialog_failure_title))
            .setContentText(message)
        dialog.setCancelable(false)
        dialog.show()
        viewModel!!.setFailMessage(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }
}