package com.dol.assessment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.dol.assessment.R
import com.dol.assessment.databinding.ProgressFooterViewBinding


class LoadStateViewHolder(private val binding: ProgressFooterViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_footer_view, parent, false)
            val binding = ProgressFooterViewBinding.bind(view)
            return LoadStateViewHolder(binding)
        }
    }

}