package com.example.root.mymvvmdemo.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.root.mymvvmdemo.R
import com.example.root.mymvvmdemo.adapters.NewsAdapter
import com.example.root.mymvvmdemo.databinding.ActivityMainBinding
import com.example.root.mymvvmdemo.model.News
import com.example.root.mymvvmdemo.viewmodel.MainViewModel
import android.app.ProgressDialog


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    var TAG = MainActivity::class.java.simpleName
    lateinit var context: Context
    lateinit var binding: ActivityMainBinding
    lateinit var rvNews: RecyclerView
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsList: ArrayList<News.ArticlesBean>
    lateinit var progress: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this@MainActivity

        init()
    }


    fun init() {
        //Class
        newsList = arrayListOf()
        newsAdapter = NewsAdapter(context, newsList)

        //Dialog
        progress = ProgressDialog(this)
        progress.setMessage("Loading News !")
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progress.setIndeterminate(true)

        //View
        rvNews = binding.rvNews
        rvNews.layoutManager = LinearLayoutManager(context)
        rvNews.adapter = newsAdapter

        //Observer ViewModel
        mainViewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)




        mainViewModel.progressObserver().observe(this, Observer {

            if (it?.progressStatus!!) {


                progress.show()


            } else {

                progress.hide()
            }


        })



        mainViewModel.getNews()?.observe(this, Observer {

            Log.e(TAG, "ArrayList Size---> " + (it?.size))
            it?.let { it1 -> newsList.addAll(it1) }
            newsAdapter.notifyDataSetChanged()

        })

    }




}
