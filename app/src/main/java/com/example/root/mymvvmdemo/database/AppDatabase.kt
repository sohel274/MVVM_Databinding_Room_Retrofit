package com.example.root.mymvvmdemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.root.mymvvmdemo.Dao.ArticleDao
import com.example.root.mymvvmdemo.model.News

@Database(entities = [News.ArticlesBean::class], version = 1, exportSchema=false)
abstract class AppDatabase :RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }



}