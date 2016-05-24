package com.erindavide.listaspesa.adapters

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.erindavide.listaspesa.R
import java.util.*

/**
 * Created by demiurgo on 5/24/16.
 */


class GroceryListAdapter(context: Context) : RecyclerView.Adapter<GroceryListAdapter.GroceryItemVH>() {

    val inflater = LayoutInflater.from(context)

    //TODO change
    val data = LinkedList<String>()


    init {
        // TODO remove
        data.add("Pane")
        data.add("Dentifricio")
        data.add("Pasta")
        data.add("Olive")
        data.add("Sedano")
        data.add("Frutta")
        notifyItemRangeInserted(0, 6)
    }

    fun addItem(value: String) {
        data.add(value)
        notifyItemInserted(data.size - 1)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GroceryItemVH {
        return GroceryItemVH(inflater.inflate(R.layout.item_grocery_list, parent, false))
    }

    override fun onBindViewHolder(holder: GroceryItemVH, position: Int) {
        holder.textView.text = data[position]
    }


    fun onItemDismiss(position: Int, viewHolder: RecyclerView.ViewHolder) {
        val item = data[position]

        data.removeAt(position);
        notifyItemRemoved(position);

        Snackbar.make(viewHolder.itemView, R.string.element_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_action_undo, {
                    data.add(position, item)
                    notifyItemInserted(position)
                }).show()
    }

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    class GroceryItemVH(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById(R.id.textView) as TextView
    }
}