package com.example.locatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locatorapp.R
import com.example.locatorapp.adapter.AddressListAdapter
import com.example.locatorapp.ui.MainActivity
import com.example.locatorapp.util.Resource
import com.example.locatorapp.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_error_message.*


class ListFragment : Fragment(R.layout.fragment_list) {


    lateinit var profileViewModel: ProfileViewModel
    lateinit var addressListAdapter: AddressListAdapter
    var isError = false
    var isLoading = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = (activity as MainActivity).profileViewModel
        setupRecyclerView()

        val navController: NavController = Navigation.findNavController(view)

        profileViewModel.getAddressRepose.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    floatingActionButton.visibility = View.VISIBLE
                    print("=====Success====")
                    response.data?.let { addressListResponse ->
                        addressListAdapter.differ.submitList(addressListResponse)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    floatingActionButton.visibility = View.VISIBLE
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    floatingActionButton.visibility = View.INVISIBLE
                }
            }
        })

        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.saveFragment);
        }
    }

    private fun setupRecyclerView() {
        addressListAdapter = AddressListAdapter()
        recyclerView.apply {
            adapter = addressListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemErrorMessage.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemErrorMessage.visibility = View.VISIBLE
        tvErrorMessage.text = message
        isError = true
    }
}