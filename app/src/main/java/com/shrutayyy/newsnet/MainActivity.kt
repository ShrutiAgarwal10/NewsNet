package com.shrutayyy.newsnet

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter : NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView:RecyclerView=findViewById(R.id.view_recycler)
        //findViewById<RecyclerView>(R.id.view_recycler).layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
        mAdapter= NewsListAdapter(this)
        //findViewById<RecyclerView>(R.id.view_recycler).adapter=adapter
        recyclerView.adapter=mAdapter

    }

    private fun fetchData()
    {
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=d0cb2d5b9cb84d4bb9ed03ba24ad0611"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)

            },
            Response.ErrorListener {

            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun itemOnClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}