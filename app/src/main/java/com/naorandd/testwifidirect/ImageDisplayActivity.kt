package com.naorandd.testwifidirect

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naorandd.testwifidirect.databinding.ActivityImageDisplayBinding


/**
 * RecyclerViewで端末内の画像を一覧表示するクラス
 */
class ImageDisplayActivity : AppCompatActivity() {
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var binding: ActivityImageDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_display)

        val galleryAdapter = GalleryAdapter{
        }

        binding.galleryImages.also { view ->
            view.layoutManager = GridLayoutManager(this, 3)
            view.adapter = galleryAdapter
        }

        viewModel.images.observe(this, Observer<List<MediaStoreImage>> { images ->
            galleryAdapter.submitList(images)
        })

        showImages()

    }

    private fun showImages() {
        viewModel.loadImages()
    }

    /**
     * RecyclerViewを生成するアダプタークラス
     */
    private inner class GalleryAdapter(val onClick: (MediaStoreImage) -> Unit) :
        ListAdapter<MediaStoreImage, ImageViewHolder>(
            MediaStoreImage.DiffCallback
        ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.gallery_layout, parent, false)
            return ImageViewHolder(view, onClick)
        }

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
        imageView.setOnClickListener {
            val image = rootView.tag as? MediaStoreImage
                ?: return@setOnClickListener
            onClick(image)
        }
    }
}