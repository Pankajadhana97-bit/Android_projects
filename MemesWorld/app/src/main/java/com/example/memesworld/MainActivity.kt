package com.example.memesworld

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var globalImgUrl : String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        progressBar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                globalImgUrl = response.getString("url")
                val memeview: ImageView = findViewById(R.id.memeImage)
                Glide.with(this).load(globalImgUrl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeview)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show()
            })

        queue.add(JsonObjectRequest)
    }

    fun shareMeme(view: View) {

        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/palin"
        intent.putExtra(Intent.EXTRA_TEXT," $globalImgUrl ")
        val chooser = Intent.createChooser(intent,"Share Using ....... ")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}