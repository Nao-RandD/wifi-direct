package com.naorandd.testwifidirect.cliant

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.naorandd.testwifidirect.R

/**
 * 送信する画面を確認するダイアログを表示するクラス
 */
class ImageConfirmDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // ダイアログビルダを生成
        val builder = AlertDialog.Builder(activity)
        // ダイアログのタイトルを設定
        builder.setTitle(R.string.dialog_title)
        // ダイアログのメッセージを設定
        builder.setMessage(R.string.dialog_msg)
        // Positive Buttonを設定
        builder.setPositiveButton(R.string.dialog_button_ok, DialogButtonClickListener())
        // Negative Buttonを設定
        builder.setNegativeButton(R.string.dialog_button_ng, DialogButtonClickListener())
        // ダイアログオブジェクトを生成し、リターン
        val dialog = builder.create()

        return dialog
    }

    /**
     * ダイアログ上のボタンのリスナ設定
     */
    private inner class DialogButtonClickListener: DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            var msg = ""
            when(which){
                // Positive Buttonならば
                DialogInterface.BUTTON_POSITIVE ->
                    msg = "fugafuga"

                // Positive Buttonならば
                DialogInterface.BUTTON_NEGATIVE ->
                    msg = "hogehoge"
            }
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }
    }
}