package com.khudyakovvladimir.vhowl.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khudyakovvladimir.vhowl.R
import com.khudyakovvladimir.vhowl.database.HighScore
import kotlinx.android.synthetic.main.item.view.*

class HighScoreAdapter(
    var context: Context,
    var list: List<HighScore>,
    //private val itemClick: (highScore: HighScore) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    inner class HighScoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(highScore: HighScore) {
            Log.d("TAG", "BIND")

            itemView.imageViewItem.setImageResource(R.drawable.cup)
            itemView.textViewItemName.text = highScore.name
            itemView.textViewItemScore.text = highScore.score.toString()

            itemView.linearLayoutItem.setOnClickListener {
               //itemClick(highScore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holderR = holder as HighScoreViewHolder
        holderR.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateAdapter(_listNews: List<HighScore>) {
        this.list.apply {
            list = emptyList()
            val tempList = list.toMutableList()
            val tempList2 = _listNews.toMutableList()
            tempList.addAll(tempList2)
            val resultList = tempList2.toList()
            list = resultList
        }
    }

}