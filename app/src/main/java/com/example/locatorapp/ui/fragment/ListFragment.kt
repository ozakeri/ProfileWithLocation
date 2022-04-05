package com.example.locatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.locatorapp.R
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration


class ListFragment : Fragment(R.layout.fragment_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController: NavController = Navigation.findNavController(view)

        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.saveFragment);
        }
    }
}