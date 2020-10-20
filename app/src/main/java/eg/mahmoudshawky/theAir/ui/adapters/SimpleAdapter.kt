package eg.mahmoudshawky.theAir.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.utils.OnItemClickListener
import eg.mahmoudshawky.theAir.utils.SimpleText
import kotlin.properties.Delegates

class SimpleAdapter(
    @LayoutRes private val layoutId: Int = R.layout.item_simple_text
) : RecyclerView.Adapter<SimpleTextViewHolder>() {

    private var list: List<SimpleText> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun updateList(newList: List<SimpleText>) {
        this.list = newList
    }

    var onItemClickListener: OnItemClickListener<SimpleText>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                layoutId,
                parent,
                false
            )
        return SimpleTextViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}

class SimpleTextViewHolder(
    view: View,
    private val onItemClickListener: OnItemClickListener<SimpleText>?
) :
    RecyclerView.ViewHolder(view) {

    fun bind(simpleText: SimpleText) {
        itemView.run {
            findViewById<TextView>(R.id.textView).run {
                text = simpleText.getText()
                setOnClickListener { onItemClickListener?.onItemClicked(simpleText) }
            }
        }
    }
}
