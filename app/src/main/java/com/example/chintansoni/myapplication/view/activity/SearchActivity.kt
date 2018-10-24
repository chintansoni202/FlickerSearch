package com.example.chintansoni.myapplication.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chintansoni.myapplication.R
import com.example.chintansoni.myapplication.model.Resource
import com.example.chintansoni.myapplication.model.Status
import com.example.chintansoni.myapplication.model.remote.response.FlickerSearchResponse
import com.example.chintansoni.myapplication.view.adapter.FlickerRecyclerAdapter
import com.example.chintansoni.myapplication.viewmodel.KotlinViewModelFactory
import com.example.chintansoni.myapplication.viewmodel.SearchActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchActivity : DaggerAppCompatActivity() {

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!adapter.isLoading()) {
                val linearLayoutManager: LinearLayoutManager = recyclerView.layoutManager as GridLayoutManager
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.itemCount - 5) {
                    viewModel.getNextPageImages()
                }
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: KotlinViewModelFactory
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        initializeViews()
        observeViewModel()
    }

    private var adapter = FlickerRecyclerAdapter()

    private fun initializeViews() {
        rv_main.adapter = adapter
        rv_main.addOnScrollListener(scrollListener)
        showEmptyView(getString(R.string.start_searching))
    }

    private lateinit var viewModel: SearchActivityViewModel

    private fun observeViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchActivityViewModel::class.java)
        viewModel.getLiveData().observe(this, Observer<Resource<FlickerSearchResponse>> { response ->
            if (response != null) {
                when (response.status) {
                    Status.LOADING -> adapter.addLoader()
                    Status.SUCCESS -> {
                        adapter.removeLoader()
                        hideEmptyView()
                        adapter.setList(response.data?.photos?.photo!!)
                    }
                    Status.ERROR -> {
                        adapter.removeLoader()
                        if (response.data == null || (response.data.photos.photo.size == 0)) {
                            adapter.clear()
                            showEmptyView(response.throwable?.message!!)
                        } else {
                            Toast.makeText(this, response.throwable?.message, Toast.LENGTH_SHORT).show()
                            hideEmptyView()
                        }
                    }
                }
            }
        })
    }

    private fun hideEmptyView() {
        textViewEmpty.visibility = View.GONE
    }

    private fun showEmptyView(message: String) {
        textViewEmpty.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchViewItem = menu.findItem(R.id.action_search)
        val searchViewAndroidActionBar = searchViewItem.actionView as SearchView
        searchViewAndroidActionBar.queryHint = "Search"
        searchViewAndroidActionBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        val disposable =
            com.jakewharton.rxbinding2.support.v7.widget.RxSearchView.queryTextChanges(searchViewAndroidActionBar)
                .debounce(600, TimeUnit.MILLISECONDS)
                .skip(1)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        adapter.clear()
                        showEmptyView(getString(R.string.start_searching))
                    } else {
                        viewModel.getFlicker(it.toString())
                    }
                }, {
                    it.printStackTrace()
                })
        compositeDisposable.add(disposable)
        return super.onCreateOptionsMenu(menu)
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        hideKeyboard(view)
    }

    private fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
