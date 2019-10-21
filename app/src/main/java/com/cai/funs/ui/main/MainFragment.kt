package com.cai.funs.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cai.funs.R
import kotlinx.android.synthetic.main.main_fragment.empty_tip
import kotlinx.android.synthetic.main.main_fragment.error_tip
import kotlinx.android.synthetic.main.main_fragment.progress_bar
import kotlinx.android.synthetic.main.main_fragment.recycler_view
import kotlinx.android.synthetic.main.main_fragment.search_view

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var viewModel: MainViewModel
  private lateinit var adpater: MuseumAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.d("MainFragment", "onViewCreated()")
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    Log.d("MainFragment", "onActivityCreated()")
    setupViewModel()

    setupUi()
  }

  private fun setupViewModel() {
    viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    // set observers
    viewModel.museums.observe(this, museumsObservers)
    viewModel.showEmptyTip.observe(this, emptyTipObserver)
    viewModel.showErrorTip.observe(this, errorTipObserver)
    viewModel.showLoading.observe(this, loadingObserver)

//    viewModel.loadMuseums()

    // set search view
    search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
      override fun onQueryTextChange(newText: String?): Boolean {
        adpater.filter.filter(newText)
        return true
      }

      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }
    })
  }

  private fun setupUi() {
    adpater = MuseumAdapter(viewModel.museums.value?.toMutableList() ?: mutableListOf())
    recycler_view.layoutManager = LinearLayoutManager(context)
    recycler_view.adapter = adpater
  }

  // observers
  private val museumsObservers = Observer<List<Museum>> {
    recycler_view.visibility = View.VISIBLE
    adpater.update(it)
  }

  private val emptyTipObserver = Observer<Boolean> {
    if (it) {
      empty_tip.visibility = View.VISIBLE
      recycler_view.visibility = View.GONE
    } else {
      empty_tip.visibility = View.GONE
    }
  }

  private val errorTipObserver = Observer<Boolean> {
    if (it) {
      error_tip.visibility = View.VISIBLE
      recycler_view.visibility = View.GONE
    } else {
      error_tip.visibility = View.GONE
    }
  }

  private val loadingObserver = Observer<Boolean> {
    if (it) {
      progress_bar.visibility = View.VISIBLE
    } else {
      progress_bar.visibility = View.GONE
    }
  }

  override fun onPause() {
    super.onPause()
    Log.d("MainFragment", "onPause()")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Log.d("MainFragment", "onSaveInstanceState()")
  }

  override fun onStop() {
    super.onStop()
    Log.d("MainFragment", "onStop()")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    Log.d("MainFragment", "onDestroyView()")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("MainFragment", "onDestroy()")
  }

  override fun onDetach() {
    super.onDetach()
    Log.d("MainFragment", "onDetach()")
  }
}
