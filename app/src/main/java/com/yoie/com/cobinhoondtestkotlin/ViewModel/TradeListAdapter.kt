package com.yoie.com.cobinhoondtestkotlin.ViewModel

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoie.com.cobinhoondtestkotlin.Model.TradeData
import java.util.ArrayList
import com.yoie.com.cobinhoondtestkotlin.databinding.TradeItemBinding

class TradeListAdapter(trades: MutableList<TradeData>) : RecyclerView.Adapter<TradeListAdapter.ViewHolder>() {
    private var trades = mutableListOf<TradeData>()

    init {
        this.trades = trades
    }

    class ViewHolder(binding: TradeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: TradeItemBinding? = null

        init {
            this.binding = binding
        }

        fun bindto(people: TradeData) {
            binding!!.obj = people
            binding!!.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TradeItemBinding.inflate(inflater, parent, false)
        //binding.getRoot().setOnClickListener(this);  //监听点击事件
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindto(trades[position])
        holder.binding!!.root.tag = trades[position]  //方便在别的地方调用
    }

    override fun getItemCount(): Int {
        return trades?.size ?: 0
    }
}