package com.naorandd.testwifidirect

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ImageDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        val textView: TextView = findViewById(R.id.text_view)

        val contentResolver = contentResolver
        var cursor: Cursor? = null
        var sb: StringBuilder? = null
        // true: images, false:audio
        // true: images, false:audio
        val flg = true

        // 例外を受け取る

        // 例外を受け取る
        try {
            cursor = if (flg) {
                // images
                contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null
                )
            } else {
                // audio
                contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                    null, null, null
                )
            }
            if (cursor != null && cursor.moveToFirst()) {
                val str = String.format(
                    "MediaStore.Images = %s\n\n", cursor.getCount()
                )
                sb = StringBuilder(str)
                do {
                    sb.append("ID: ")
                    sb.append(
                        cursor.getString(
                            cursor.getColumnIndex(
                                MediaStore.Images.Media._ID
                            )
                        )
                    )
                    sb.append("\n")
                    sb.append("Title: ")
                    sb.append(
                        cursor.getString(
                            cursor.getColumnIndex(
                                MediaStore.Images.Media.TITLE
                            )
                        )
                    )
                    sb.append("\n")
                    sb.append("Path: ")
                    sb.append(
                        cursor.getString(
                            cursor.getColumnIndex(
                                MediaStore.Images.Media.DATA
                            )
                        )
                    )
                    sb.append("\n\n")
                } while (cursor.moveToNext())
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val toast = Toast.makeText(
                this,
                "例外が発生、Permissionを許可していますか？", Toast.LENGTH_SHORT
            )
            toast.show()

            //MainActivityに戻す
            finish()
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }

        textView.text = sb
    }
}