package com.example.affirmations.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp1.DetailActivity
import com.example.madcamp1.R
import com.example.madcamp1.model.Imageres

class ItemAdapter(
    private val context: Context,
    private val dataset: MutableList<Imageres>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    fun addImage(imageres: Imageres){
        dataset.add(imageres)
        notifyDataSetChanged()
    }

    fun getSize():Int{
        if (dataset.size > 0){ return dataset.size-1}
        else{
            return 0
        }
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    val TAG: String = "로그"

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        Log.d(TAG, "ItemAdapter - onCreateViewHolder() called")
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_image, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        Log.d(TAG, "ItemAdapter - onBindViewHolder() called")
        holder.imageView.setImageResource(item.imageResourceId)
        holder.imageView.background = context.getDrawable(R.drawable.round_outline)
        holder.imageView.clipToOutline = true
        holder.imageView.setOnClickListener{
            val context = holder.view.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("position",item.imageResourceId)
            context.startActivity(intent)

        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}