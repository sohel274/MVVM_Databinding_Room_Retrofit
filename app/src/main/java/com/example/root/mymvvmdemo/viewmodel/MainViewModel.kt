package com.example.root.mymvvmdemo.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.root.mymvvmdemo.MyApplication
import com.example.root.mymvvmdemo.database.AppDatabase
import com.example.root.mymvvmdemo.model.News
import com.example.root.mymvvmdemo.model.ProgressStatus
import com.example.root.mymvvmdemo.retrofit.APIClient
import com.example.root.mymvvmdemo.retrofit.APIInterface
import com.example.root.mymvvmdemo.utility.Url
import com.example.root.mymvvmdemo.utility.Utility
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainViewModel : ViewModel() {

    var articleList: MutableLiveData<ArrayList<News.ArticlesBean>>? = null
    var progressStatus: MutableLiveData<ProgressStatus>? = null


    fun progressObserver(): MutableLiveData<ProgressStatus> {

        if (progressStatus == null) {
            progressStatus = MutableLiveData()
        }

        return progressStatus as MutableLiveData<ProgressStatus>
    }

    fun getNews(): MutableLiveData<ArrayList<News.ArticlesBean>>? {


        if (articleList == null) {
            articleList = MutableLiveData()

            if (Utility.isInternetConnect(MyApplication.getAppContext())) {
                setProgressStatus(true)
                loadArticle()
            } else {

                getArticleFromDb()
            }

        }

        return articleList

    }


    public fun setProgressStatus(boolean: Boolean) {

        var status = ProgressStatus()
        status.progressStatus = boolean
        progressStatus?.value = status

    }


    fun loadArticle() {


        var retrofitClient: APIClient = APIClient()
        var apiInterface: APIInterface = retrofitClient.getClient().create(APIInterface::class.java)
        var call: Call<News> = apiInterface.getNews(Url.SOURCE, Url.API_KEY)




        call.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {

                //finally we are setting the list to our MutableLiveData
                setProgressStatus(false)
                var news: News = response.body()!!
                articleList?.value = news.articles
                news.articles?.let { saveDataToDb(it) }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.e("TAG", "OnFailure---> " + t.toString())
                setProgressStatus(false)

            }
        })

    }


    fun getArticleFromDb() {

        var arrayList: ArrayList<News.ArticlesBean> = arrayListOf()
        Observable.fromCallable({

            var db: AppDatabase = AppDatabase.getAppDataBase(MyApplication.getAppContext())!!
            var list = db.articleDao().getArticle()
            arrayList.addAll(list)
            db.close()


        }).doOnNext({list  ->



        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    articleList?.value = arrayList
                })


      /*  Observable.from(uris)
                .flatMap(object : Func1<Uri, Observable<Void>>() {
                    fun call(uri: Uri): Observable<Void> {
                        return createDoSomethingObservable(uri)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Void>() {
                    fun onCompleted() {
                        Log.d(TAG, "completed")
                    }

                    fun onError(e: Throwable) {

                    }

                    fun onNext(aVoid: Void) {
                        Log.d(TAG, "next")
                    }
                })*/





    }


    fun saveDataToDb(articleList:ArrayList<News.ArticlesBean>){
        var db: AppDatabase = AppDatabase.getAppDataBase(MyApplication.getAppContext())!!


        Observable.fromCallable({

            db.articleDao().deleteAllArticle()

            for(i in articleList.indices){

                var article =  articleList[i]
                db.articleDao().insertArticle(article)

            }

            db.close()



        }).doOnNext({list  ->

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()






    }


}