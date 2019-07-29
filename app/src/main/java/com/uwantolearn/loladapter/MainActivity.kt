package com.uwantolearn.loladapter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listOfString = ('A'..'Z').map(Char::toString).toList()
        val adapter = LolAdapter()
        adapter.let(recyclerView::setAdapter)
        LinearLayoutManager(this).let(recyclerView::setLayoutManager)
        adapter.add(listOfString)
        DividerItemDecoration(this, LinearLayoutManager.VERTICAL).let(recyclerView::addItemDecoration)

        adapter.clickObservable().subscribe {
           when(it.first){
                AdapterClick.TITLE -> println(it.second)
                AdapterClick.IMAGE -> println(it.second)
                AdapterClick.BUTTON -> println(it.second)
           }
        }
    }
}


enum class AdapterClick {
    TITLE,
    IMAGE,
    BUTTON
}

class LolAdapter : SimpleAdapter<String, AdapterClick>(R.layout.row) {
    override fun createViewHolder(view: View): SimpleViewHolder<String, AdapterClick> = LolViewHolder(view)
}

class LolViewHolder(private val view: View) : SimpleViewHolder<String, AdapterClick>(view) {
    override fun bind(data: String, click: (AdapterClick) -> Unit) {
        view.title.setOnClickListener { click(AdapterClick.TITLE) }
        view.button.setOnClickListener { click(AdapterClick.BUTTON) }
        view.imageView.setOnClickListener { click(AdapterClick.IMAGE) }
    }
}
