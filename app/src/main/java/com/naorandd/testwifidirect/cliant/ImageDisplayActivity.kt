package com.naorandd.testwifidirect.cliant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naorandd.testwifidirect.util.CommonDefine
import com.naorandd.testwifidirect.R
import com.naorandd.testwifidirect.databinding.ActivityImageDisplayBinding


/**
 * RecyclerViewで端末内の画像を一覧表示するクラス
 */
class ImageDisplayActivity : AppCompatActivity() {
    private val mViewModel: ImageViewModel by viewModels()
    private lateinit var binding: ActivityImageDisplayBinding

    // 画像一覧表示するViewを生成
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_image_display
        )

        // アダプターインスタンスを生成
        val galleryAdapter = GalleryAdapter{image->
            selectImage(image)
        }

        // レイアウトファイル（galleryImages）にレイアウトマネージャーとアダプタを登録
        binding.galleryImages.also { view ->
            view.layoutManager = GridLayoutManager(this, 3)
            view.adapter = galleryAdapter
        }

        // 画像の更新を受け取るObserverをアダプターに登録
        mViewModel.images.observe(this, Observer<List<MediaStoreImage>> { images ->
            galleryAdapter.submitList(images)
        })

        // ViewModelから画像を読み込み
        mViewModel.loadImages()

    }
    /**
     * 送付用に選択した画像の確認画面を表示するメソッド
     */
    private fun selectImage(image: MediaStoreImage){
        // 確認用のダイアログ表示
        val dialogFragment = ImageConfirmDialogFragment()
        dialogFragment.show(supportFragmentManager, "ImageConfirmDialogFragment")
    }

    /**
     * RecyclerViewを生成するアダプタークラス
     */
    private inner class GalleryAdapter(val onClick: (MediaStoreImage) -> Unit) :
        ListAdapter<MediaStoreImage, ImageViewHolder>(
            MediaStoreImage.DiffCallback
        ) {

        // 親ViewにViewHolderのViewを表示するためのメソッド
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.gallery_layout, parent, false)
            return ImageViewHolder(
                view,
                onClick
            )
        }

        // ViewHolderに表示するCardViewを登録
        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val mediaStoreImage = getItem(position)
            holder.rootView.tag = mediaStoreImage
            holder.textView.text = mediaStoreImage.displayName

            Glide.with(holder.imageView)
                .load(mediaStoreImage.contentUri)
                .thumbnail(CommonDefine.THUMBNAIL_SIZE)
                .centerCrop()
                .into(holder.imageView)
        }
    }
}

/**
 * ViewHolderクラス
 */
private class ImageViewHolder(view: View, onClick: (MediaStoreImage) -> Unit) :
    RecyclerView.ViewHolder(view) {
    val rootView = view
    val imageView: ImageView = view.findViewById(R.id.image)
    val textView: TextView = view.findViewById(R.id.text)

    init {
        // ViewにリスナーでOnClickを割り当てる
        imageView.setOnClickListener {
            val image = rootView.tag as? MediaStoreImage
                ?: return@setOnClickListener
            onClick(image)
        }
    }
}