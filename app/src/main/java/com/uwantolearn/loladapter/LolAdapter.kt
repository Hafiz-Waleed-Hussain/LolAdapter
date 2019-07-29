package com.uwantolearn.loladapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.row.view.*
import java.util.function.Consumer


abstract class SimpleAdapter<T, E : Enum<E>>(@LayoutRes val id: Int) : RecyclerView.Adapter<SimpleViewHolder<T, E>>() {

    private val data = mutableListOf<T>()
    private val clickSubject: PublishSubject<Pair<Enum<E>, T>> = PublishSubject.create()

    fun clickObservable() = clickSubject.hide()


    abstract fun createViewHolder(view: View): SimpleViewHolder<T, E>

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T, E> =
        parent.context
            .let(LayoutInflater::from)
            .inflate(viewType, parent, false)
            .let(::createViewHolder)

    override fun getItemViewType(position: Int): Int = id

    override fun onBindViewHolder(holder: SimpleViewHolder<T, E>, position: Int) {
        holder.bind(data[position], { clickSubject.onNext(Pair(it, data[position])) })
    }

    fun add(data: List<T>) = this.data.addAll(data)
}


abstract class SimpleViewHolder<T, E : Enum<E>>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(data: T, click: (E) -> Unit)

}


