package com.rahat.test_retrofit.view.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rahat.test_retrofit.R
import com.rahat.test_retrofit.data.network.MovieModel
import com.rahat.test_retrofit.utils.HelperClass

class MovieListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {

        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_blog,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewholder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getList(): List<MovieModel> {
        return differ.currentList
    }
    fun submitList(list: List<MovieModel>) {
        differ.submitList(list)
    }

    class BlogViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_view_name)
        val blogImage: ImageView = itemView.findViewById(R.id.blog_image)
        val blogDesc: TextView = itemView.findViewById(R.id.text_view_desc)

        fun bind(item: MovieModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            blogDesc.text = item.overview
            textView.text = item.title
            blogImage.load(HelperClass.Image_PATH + item.poster_path) {
                crossfade(true)

            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: MovieModel)
    }
}
