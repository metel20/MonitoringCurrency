package com.metel20.presentation.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.metel20.presentation.databinding.CurrencyPairBinding
import com.metel20.presentation.databinding.EmptyBinding
import com.metel20.presentation.databinding.ErrorBinding
import com.metel20.presentation.databinding.ProgressBinding


class DashboardAdapter(
    private val retry: Retry,
    private val types: List<TypeUi> = listOf(
        TypeUi.Base,
        TypeUi.Progress,
        TypeUi.Empty,
        TypeUi.Error
    )
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(), ShowList {

    private val currencies = mutableListOf<DashboardUi>()

    override fun show(list: List<DashboardUi>) {
        val diffResult =
            DiffUtil.calculateDiff(DashboardDiffUtilCallback(oldList = currencies, newList = list))
        currencies.clear()
        currencies.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        types[viewType].viewHolder(parent, retry)

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    abstract class DashboardViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        open fun bind(item: DashboardUi) = Unit

        class Progress(binding: ProgressBinding) : DashboardViewHolder(binding.root)

        class Empty(binding: EmptyBinding) : DashboardViewHolder(binding.root)

        class Error(private val binding: ErrorBinding, private val clickListener: Retry) :
            DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)
                binding.retryButton.setOnClickListener { clickListener.retry() }
            }
        }

        class Base(private val binding: CurrencyPairBinding) : DashboardViewHolder(binding.root) {

            override fun bind(item: DashboardUi) {
                item.show(binding)
            }
        }
    }
}