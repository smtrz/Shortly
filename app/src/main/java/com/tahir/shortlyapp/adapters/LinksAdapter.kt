package com.tahir.shortlyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tahir.shortlyapp.R
import com.tahir.shortlyapp.databinding.ShorturlItemBinding
import com.tahir.shortlyapp.db.Links
import kotlinx.android.synthetic.main.shorturl_item.view.*

class LinksAdapter(context: Context) : RecyclerView.Adapter<LinksAdapter.ViewHolder>() {
    lateinit var clickListener: OnItemClickListener
    private val context: Context = context
    private lateinit var binding: ShorturlItemBinding
    var positionForCopy: Int = -1
    private lateinit var items: MutableList<Links>

    /**
     * sets the reference of OnItemClickListener
     * @param clickListener
     */
    fun setupClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    /**
     * sets the list and call notifyDataSetChanged() and resets the value on selection
     * @param link_list
     */
    fun setList(link_list: MutableList<Links>) {
        items = link_list
        positionForCopy = -1
        notifyDataSetChanged()
    }

    /**
     * Sets the binding instance using DataBindingUtil, Called when RecyclerView needs a new
     * RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.shorturl_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    /** Class created to sets up the click listeners, update views. */
    inner class ViewHolder(val binding: ShorturlItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: Links) {
            binding.shortlinkdata = item
            binding.executePendingBindings()
            binding.root.delete.setOnClickListener(this)
            binding.root.copy.setOnClickListener(this)
        }

        /**
         * calls the interface method onItemClick when on click is pressed, button copy is clicked
         * which call notifydatasetChange so that onBindView Can be called for changing the color of
         * button.
         */
        override fun onClick(v: View?) {
            clickListener.onItemClick(
                binding.shortlinkdata!!,
                position = absoluteAdapterPosition,
                v
            )
            if (v?.id == R.id.copy) {
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position also change the button
     * color based on user click for copy.
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items?.get(position)!!)
        // if the click button has reached , change the background color to darkviolet else to cyan
        if (positionForCopy == position) {

            holder.binding.copy.setBackgroundColor(context.resources.getColor(R.color.darkviolet))
        } else {
            holder.binding.root.copy.setBackgroundColor(context.resources.getColor(R.color.cyan))
        }
    }

    override fun getItemCount(): Int = items!!.size

    fun updatePositionForCopyButton(pos: Int) {
        positionForCopy = pos
    }
}

/**
 * interface to handle the click events of the recycleview item.
 * @param listitem
 * @param position
 * @param view
 */
interface OnItemClickListener {
    fun onItemClick(listitem: Links, position: Int, view: View?)
}
