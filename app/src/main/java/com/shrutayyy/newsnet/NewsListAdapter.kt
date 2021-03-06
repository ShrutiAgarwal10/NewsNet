package com.shrutayyy.newsnet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsListAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

             val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)

             val viewHolder=NewsViewHolder(view)

             view.setOnClickListener{
                 listener.itemOnClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.authorView.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView:TextView=itemView.findViewById(R.id.txt_title_news_item)
    val imageView: ImageView =itemView.findViewById(R.id.image)
    val authorView:TextView=itemView.findViewById(R.id.author)

}

interface NewsItemClicked
{
    fun itemOnClicked(item:News){

    }
}