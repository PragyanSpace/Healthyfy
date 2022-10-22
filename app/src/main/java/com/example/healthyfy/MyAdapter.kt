package com.example.healthyfy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val TheDatas:ArrayList<HData>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mListner:onItemClickListener
    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListner=listener
    }
    inner class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
    {
        val hName:TextView=itemView.findViewById(R.id.hospitalName)
        val hLoc:TextView=itemView.findViewById(R.id.hospitalLocation)
        val hNum:TextView=itemView.findViewById(R.id.hospitalNum)

        init {
            itemView.setOnClickListener{
                listener.onItemClicked(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.hospital_card, parent, false)
        return MyViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val current=TheDatas[position]
            holder.hName.text=current.hname
            holder.hLoc.text=current.hloc
            holder.hNum.text=current.hnum
        }
    }

    override fun getItemCount(): Int {
        return TheDatas.size
    }
}