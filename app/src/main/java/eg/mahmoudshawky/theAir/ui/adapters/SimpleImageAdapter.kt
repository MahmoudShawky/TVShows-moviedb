package eg.mahmoudshawky.theAir.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.utils.OnItemClickListener
import eg.mahmoudshawky.theAir.utils.RemoteConfig
import eg.mahmoudshawky.theAir.utils.SimpleImageText
import kotlin.properties.Delegates

class SimpleImageAdapter(
    @LayoutRes private val layoutId: Int = R.layout.item_simple_image_text
) : RecyclerView.Adapter<SimpleImageTextViewHolder>() {

    private var list: List<SimpleImageText> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<SimpleImageText>) {
        this.list = newList
    }

    var onItemClickListener: OnItemClickListener<SimpleImageText>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleImageTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                layoutId,
                parent,
                false
            )
        return SimpleImageTextViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SimpleImageTextViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

}

class SimpleImageTextViewHolder(
    view: View,
    private val onItemClickListener: OnItemClickListener<SimpleImageText>?
) :
    RecyclerView.ViewHolder(view) {

    fun bind(simpleImageText: SimpleImageText) {
        itemView.run {
            findViewById<TextView>(R.id.textView).run {
                text = simpleImageText.getText()
            }

            simpleImageText.getImagePath()?.let {
                findViewById<AppCompatImageView>(R.id.ivPoster).run {
                    Glide
                        .with(this)
                        .load(RemoteConfig.getThumbnailsUrl(it))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(this)
                }
            }

            setOnClickListener { onItemClickListener?.onItemClicked(simpleImageText) }
        }
    }
}
