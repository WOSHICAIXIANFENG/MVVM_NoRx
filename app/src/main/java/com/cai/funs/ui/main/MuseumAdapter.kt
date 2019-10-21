package com.cai.funs.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cai.funs.R
import com.cai.funs.model.MuseumData
import kotlinx.android.synthetic.main.item_museum.view.museum_img
import kotlinx.android.synthetic.main.item_museum.view.museum_name
import kotlinx.android.synthetic.main.item_museum_img.view.mu_img
import java.lang.IllegalStateException

typealias Museum = MuseumData

class MuseumAdapter(private var museums: MutableList<Museum>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

  private var museumsListFull: List<Museum> = museums.toList()

  override fun getItemViewType(position: Int): Int {
    return if (position % 2 == 0) ITEM_TYPE_IMG else ITEM_TYPE
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ITEM_TYPE_IMG -> {
        val view =
          LayoutInflater.from(parent.context).inflate(R.layout.item_museum_img, parent, false)
        M2ViewHolder(view)
      }

      ITEM_TYPE -> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_museum, parent, false)
        MViewHolder(view)
      }

      else -> {
        throw IllegalStateException("The time type is unknown")
      }
    }
  }

  override fun getItemCount(): Int {
    return museums.size
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val museum = museums[position]
    when (holder.itemViewType) {
      ITEM_TYPE_IMG -> {
        Glide.with((holder as M2ViewHolder).image.context).load(museum.imgUrl).into(holder.image)
      }
      ITEM_TYPE -> {
        (holder as MViewHolder).name.text = museum.name
        Glide.with(holder.image.context).load(museum.imgUrl).into(holder.image)
      }
    }
  }

  // update data set when we get data from network
  fun update(data: List<Museum>) {
    museums.clear()
    museums.addAll(data)
    museumsListFull = museums.toList()
    notifyDataSetChanged()
  }

  // Companion objects and their members can only be accessed via the containing class name
  private companion object {
    private const val ITEM_TYPE_IMG = 0
    private const val ITEM_TYPE = 1
  }

  private class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.museum_name
    val image: ImageView = view.museum_img
  }

  private class M2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = view.mu_img
  }

  override fun getFilter(): Filter {
    return myFilter
  }

  private val myFilter = object : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
//      val mutableList = mutableListOf<Museum>()
//      if (constraint == null || constraint.isEmpty()) {
//        mutableList.addAll(museumsListFull)
//      } else {
//        mutableList.addAll(museumsListFull.filter { it.name.contains(constraint.toString(), true) })
//      }
//
//      val filterResults = FilterResults()
//      filterResults.values = mutableList
//      return filterResults
      return if (constraint == null || constraint.isEmpty()) {
        FilterResults().apply {
          values = museumsListFull
        }
      } else {
        FilterResults().apply {
          values = museumsListFull.filter { it.name.contains(constraint.toString(), true) }
        }
      }
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults?) {
      museums.clear()
      museums.addAll(results?.values as List<Museum>)
      notifyDataSetChanged()
    }
  }
}