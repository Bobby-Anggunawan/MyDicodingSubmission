package id.chainlizard.saltiesmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.databinding.LoaderPagingBinding

object MovieLoadState {

    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.loader_paging, parent, false)
    ) {
        private val binding = LoaderPagingBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.pbar
        private val errorMsg: TextView = binding.pesanError
        private val retry: Button = binding.retry
            .also {
                it.setOnClickListener { retry() }
            }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    class MyLoadStateAdapter(
        private val retry: () -> Unit
    ) : LoadStateAdapter<LoadStateViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
        ) = LoadStateViewHolder(parent, retry)

        override fun onBindViewHolder(
            holder: LoadStateViewHolder,
            loadState: LoadState
        ) = holder.bind(loadState)

        override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
            return loadState is LoadState.Loading || loadState is LoadState.Error || loadState is LoadState.NotLoading
        }
    }
}