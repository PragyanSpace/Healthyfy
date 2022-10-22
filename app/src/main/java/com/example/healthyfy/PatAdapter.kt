package com.example.healthyfy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatAdapter(val MedData:ArrayList<pData>):RecyclerView.Adapter<PatAdapter.MyViewHolder>()
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
        val mName:TextView=itemView.findViewById(R.id.medicineName)
        val mNoOfTime:TextView=itemView.findViewById(R.id.noOfTime)
        val mTime:TextView=itemView.findViewById(R.id.pillsPerDay)

        init {
            itemView.setOnClickListener{
                listener.onItemClicked(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.medicine_card, parent, false)
        return MyViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val current=MedData[position]
            holder.mName.text=current.mName
            holder.mNoOfTime.text=current.noOftime
            holder.mTime.text=current.time
        }
    }

    override fun getItemCount(): Int {
        return MedData.size
    }
}