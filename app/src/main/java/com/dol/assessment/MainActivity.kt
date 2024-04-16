package com.dol.assessment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.dol.assessment.data.model.Post
import com.dol.assessment.data.model.PostsResponse
import com.dol.assessment.databinding.ActivitymainBinding
import com.dol.assessment.recyclerview.CustomLoadStateAdapter
import com.dol.assessment.recyclerview.RecyclerViewAdapter
import com.dol.assessment.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notify

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitymainBinding

    private var adapter: RecyclerViewAdapter? = null

    private var selectPosts: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitymainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel by viewModels<MainViewModel>()

        setupRecyclerView(viewModel)

    }

    private fun setupRecyclerView(viewModel: MainViewModel) {
        binding.rvMain.adapter = adapter

        selectPosts?.cancel()
        selectPosts = lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collectLatest {
                    adapter?.submitData(it)
                }
            }
        }

        binding.rvMain.adapter = adapter?.withLoadStateFooter(
            footer = CustomLoadStateAdapter {
                adapter?.retry()
            }
        )

        lifecycleScope.launch {
            adapter?.loadStateFlow?.collect { loadState ->
                val refreshState = loadState.refresh

                binding.rvMain.isVisible = refreshState is LoadState.NotLoading

                if (refreshState is LoadState.Error) {
                    showErrorSnackBar()
                }

                val errorState = loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    showErrorSnackBar()
                }
            }
        }
    }

    private fun showErrorSnackBar() {
        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                adapter?.refresh()
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.black))
            .show()
    }
}