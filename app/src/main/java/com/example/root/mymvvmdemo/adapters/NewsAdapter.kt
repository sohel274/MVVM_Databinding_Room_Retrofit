package com.example.root.mymvvmdemo.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.root.mymvvmdemo.R
import com.example.root.mymvvmdemo.databinding.NewsListItemBinding
import com.example.root.mymvvmdemo.model.News

class NewsAdapter(private val ctx: Context, private var newsList: ArrayList<News.ArticlesBean>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var layoutInflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        var binding: NewsListItemBinding = DataBindingUtil.inflate(this!!.layoutInflater!!, R.layout.news_list_item, parent, false)
        return ViewHolder(binding)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.news = newsList[position]


    }


    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(var binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {}

}