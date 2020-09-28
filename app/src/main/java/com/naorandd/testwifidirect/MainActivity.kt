package com.naorandd.testwifidirect

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.naorandd.testwifidirect.cliant.ImageDisplayActivity
import com.naorandd.testwifidirect.util.AnimationUtil
import com.naorandd.testwifidirect.util.CommonDefine

class MainActivity : AppCompatActivity() {

    private lateinit var mConstraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mConstraintLayout = findViewById(R.id.main_constraint_layout)

        //背景アニメーションを設定
        AnimationUtil.animateConstraintLayout(mConstraintLayout,
                CommonDefine.BACKGROUND_ENTER_ANIMATION_DURATION, CommonDefine.BACKGROUND_EXIT_ANIMATION_DURATION)

        val bt_start = findViewById<Button>(R.id.button_start)
        bt_start.setOnClickListener {
            startActivity()
        }

        // 画像一覧を内部ストレージより取得して表示
        if(Build.VERSION.SDK_INT >= 23){
            prepareShow()
        }
        else{
            showImages()
        }
    }

    /**
     * タイトルからボタンが押された時の処理をするメソッド
     */
    private fun startActivity(){
        showImages()
    }

    /**
     * 内部ストレージへのパーミッションを確認するメソッド
     */
    private fun prepareShow() {
        if (haveStoragePermission()) {
            showImages()
        }else{
            requestPermission()
        }
    }

    /**
     * 端末内の画像を一覧表示するアクティビティへ遷移するメソッド
     */
    private fun showImages() {
        val intent = Intent(this, ImageDisplayActivity::class.java)
        startActivity(intent)
    }

    /**
     * パーミッション取得後の処理
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // リクエストコードによって処理を分岐
        when (requestCode) {
            CommonDefine.READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages()
                }else {
                    // それでも拒否された時の対応
                    val toast = Toast.makeText(this,
                    "なにも表示できません", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    /**
     * 内部ストレージへのパーミッションがあるかを確認するメソッド
     */
    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * 内部ストレージへのパーミッションがなければユーザーに許可を求めるメソッド
     */
    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            // ユーザに許可を求める画面表示
            ActivityCompat.requestPermissions(this, permissions, CommonDefine.READ_EXTERNAL_STORAGE_REQUEST)
        }
    }
}