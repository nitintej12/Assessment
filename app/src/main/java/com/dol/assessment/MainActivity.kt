package com.dol.assessment

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dol.assessment.data.model.PostsResponse
import com.dol.assessment.databinding.ActivitymainBinding
import com.dol.assessment.recyclerview.RecyclerViewAdapter
import com.dol.assessment.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitymainBinding

    private var adapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitymainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel by viewModels<MainViewModel>()



        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect {
                    if (it.isLoading) {
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.rvMain.visibility = View.GONE
                    }
                    if (it.isError) {
                        showErrorSnackBar(viewModel)
                    }
                    if (it.postsList != null) {
                        binding.progressCircular.visibility = View.GONE
                        binding.rvMain.visibility = View.VISIBLE
                        setupRecyclerView(it.postsList)
                    }
                }
            }
        }


    }

    private fun setupRecyclerView(response: PostsResponse) {
        val recyclerView = binding.rvMain
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = RecyclerViewAdapter(this, response)
        recyclerView.setAdapter(adapter)
    }

    private fun showErrorSnackBar(viewModel: MainViewModel) {
        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getPosts()
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.black))
            .show()
    }
}