package com.example.root.mymvvmdemo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


class News {


    var articles: ArrayList<ArticlesBean>? = null

    var totalResults: Int = 0

    var status: String? = null


    @Entity
    class ArticlesBean {

        companion object {
            @BindingAdapter("bind:imageUrl")
            @JvmStatic
            fun loadImage(view: ImageView, imageUrl: String) {
                Glide.with(view.context).load(imageUrl).into(view);
            }

        }

        @PrimaryKey(autoGenerate = true)
        var id:Int?=null

        var content: String? = null

        var publishedAt: String? = null

        @ColumnInfo(name = "urlToImage")
        var urlToImage: String? = null

        var url: String? = null

        @ColumnInfo(name = "description")
        var description: String? = null

        @ColumnInfo(name = "title")
        var title: String? = null

        var author: String? = null

    //    var source: SourceBean? = null


    }

    /*class SourceBean {

        var name: String? = null

       // var id: String? = null
    }*/


}
