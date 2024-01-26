package com.teumteum.base.component.snackbar

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.teumteum.base.R
import com.teumteum.base.databinding.SnackbarButtonBinding
import com.teumteum.base.util.extension.setOnSingleClickListener

class ButtonSnackBar(view: View, private val message: String,
                     private val buttonText: String,
                     private val onClickListener: (View?) -> Unit) {

    companion object {
        fun make(view: View, message: String, buttonText: String, onClickListener: (View?) -> Unit) =
            ButtonSnackBar(view, message, buttonText, onClickListener)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
    private val inflater = LayoutInflater.from(context)
    private val snackBarBinding: SnackbarButtonBinding = DataBindingUtil.inflate(inflater, R.layout.snackbar_button, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackBarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            addView(snackBarBinding.root, 0)
        }
    }

    private fun initData() {
        with(snackBarBinding) {
            tvSnackbar.text = message
            tvSnackbarButton.text = buttonText
            tvSnackbarButton.setOnSingleClickListener{ onClickListener }
        }
    }

    fun setAnchorView(view: View?) {
        snackBar.apply {
            anchorView = view
        }
    }

    fun show() {
        snackBar.show()
    }
}

/**

 *     private val context = view.context
 *     private val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
 *     private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
 *
 *     private val inflater = LayoutInflater.from(context)
 *     private val snackBarBinding: SnackbarDefaultBinding = DataBindingUtil.inflate(inflater, R.layout.snackbar_default, null, false)
 *
 *     init {
 *         initView()
 *         initData()
 *     }
 *
 *     private fun initView() {
 *         with(snackBarLayout) {
 *             removeAllViews()
 *             setPadding(0, 0, 0, 0)
 *             setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
 *             addView(snackBarBinding.root, 0)
 *         }
 *     }
 *
 *     private fun initData() {
 *         with(snackBarBinding) {
 *             tvSnackbarDefault.text = message
 *         }
 *     }
 *
 *     fun setAnchorView(view: View?) {
 *         snackBar.apply {
 *             anchorView = view
 *         }
 *     }
 *
 *     fun show() {
 *         snackBar.show()
 *     }
 * }
 */