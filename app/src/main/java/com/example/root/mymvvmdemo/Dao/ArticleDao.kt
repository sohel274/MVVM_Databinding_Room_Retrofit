package com.example.root.mymvvmdemo.Dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.root.mymvvmdemo.model.News


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: News.ArticlesBean)


    @Query("SELECT * FROM ArticlesBean")
    fun getArticle(): List<News.ArticlesBean>


    @Query("DELETE FROM ArticlesBean")
    public fun deleteAllArticle()


}