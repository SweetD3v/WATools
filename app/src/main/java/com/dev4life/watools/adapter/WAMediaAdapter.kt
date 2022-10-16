package com.dev4life.watools.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev4life.watools.databinding.ItemStatusBinding
import com.dev4life.watools.models.Media
import com.dev4life.watools.ui.activities.FullViewWhatsappActivity
import com.dev4life.watools.utils.getVideoThumbnail

class WAMediaAdapter(
    var ctx: Context,
    var mediaList: MutableList<Media>,
    relativeLayout: RelativeLayout
) :
    RecyclerView.Adapter<WAMediaAdapter.VH>() {
    class VH(var binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemStatusBinding.inflate(LayoutInflater.from(ctx), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val media = mediaList[holder.adapterPosition]
        Log.e("TAG", "onBindViewHolder: ${media.uri}")
        if (media.isVideo) {
            Glide.with(ctx).load(getVideoThumbnail(ctx, media.uri))
                .into(holder.binding.ivThumbnail)
            holder.binding.imgPlay.visibility = View.VISIBLE
        } else {
            Glide.with(ctx).load(media.uri).into(holder.binding.ivThumbnail)
            holder.binding.imgPlay.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            ctx.startActivity(
                Intent(ctx, FullViewWhatsappActivity::class.java)
                    .putExtra("position", holder.adapterPosition)
                    .putExtra(
                        "type",
                        if (mediaList[holder.adapterPosition].isVideo) "video"
                        else "photo"
                    )
            )
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }
}