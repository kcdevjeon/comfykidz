package com.kccorp.comfykids

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kccorp.comfykids.databinding.ItemResultBinding

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val results = mutableListOf<Result>()
        for (i in 1.. 10) {
            results.add(Result("9/$i", "Today is "))
        }

        val historyRecyclerView = findViewById<RecyclerView>(R.id.history_recycler_view)
        historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = ResultAdapter(results)
        }
    }
}

class ResultAdapter(val items: List<Result>) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>(){
    class ResultViewHolder(val binding: ItemResultBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(ItemResultBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binding.resultValue = items[position]
    }

    override fun getItemCount() = items.size


}