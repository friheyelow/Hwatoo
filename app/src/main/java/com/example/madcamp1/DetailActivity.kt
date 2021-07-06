package com.example.madcamp1


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_detail)
        val imageView : ImageView = findViewById(R.id.image_detail)
        val images=intent!!.getIntExtra("position",1) //images=item.imageResourceId
        imageView.setImageResource(images)
    }
}