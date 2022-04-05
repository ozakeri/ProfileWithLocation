package com.example.locatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.locatorapp.R
import com.example.locatorapp.ui.MainActivity
import com.example.locatorapp.util.Resource
import com.example.locatorapp.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(R.layout.fragment_list) {


    lateinit var profileViewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = (activity as MainActivity).profileViewModel

        val navController: NavController = Navigation.findNavController(view)

        profileViewModel.getAddressRepose.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { addressListResponse ->
                        print("profileResponse====" + addressListResponse.address)
                    }
                }

                is Resource.Error -> {
                response.message?.let {message ->
                    Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                        .show()
                }
                }

                is Resource.Loading -> {
                    print("====Loading====")
                }
            }
        })

        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.saveFragment);
        }
    }
}