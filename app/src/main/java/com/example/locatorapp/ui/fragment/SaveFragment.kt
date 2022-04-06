package com.example.locatorapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.locatorapp.ProfileApplication
import com.example.locatorapp.R
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.ui.MainActivity
import com.example.locatorapp.util.Resource
import com.example.locatorapp.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment(R.layout.fragment_save) {

    lateinit var profileViewModel: ProfileViewModel
    //lateinit var region,address,lat,lng,coordinate_mobile,coordinate_phone_number,first_name,last_name,gender

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = (activity as MainActivity).profileViewModel
        val navController: NavController = Navigation.findNavController(view)
        profileViewModel.saveAddressRepose.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    response.data?.let { saveResponse ->
                        print("profileResponse====" + saveResponse.address)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    print("====Loading====")
                }
            }

        })

        btn_save.setOnClickListener {

            val name = edt_name?.text.toString().trim()
            val family = edt_family?.text.toString().trim()
            val phone = edt_phone?.text.toString().trim()
            val mobileNumber = edt_mobileNumber?.text.toString().trim()
            val address = edt_address?.text.toString().trim()
            val context = view.context

            val check = chekInput(context,name,family,phone,mobileNumber,address)

            if (check){
                val request = RequestBean(
                    1,
                    address,
                    mobileNumber,
                    phone,
                    name,
                    "MALE",
                    family,
                    35.7717503,
                    51.3365315
                )
                profileViewModel.getSaveAddressResponse(request)
            }


            //navController.navigate(R.id.mapFragment);
        }
    }


    fun chekInput(context: Context,
        name: String,
        family: String,
        phone: String,
        mobileNumber: String,
        address: String
    ): Boolean {
        val requestBean: RequestBean? = null


        if (!name.isNullOrBlank()) {
            requestBean?.first_name = name
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)
        } else {
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_remove_circle)
            return false
        }


        if (!family.isNullOrBlank()) {
            requestBean?.last_name = family
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)
        } else {
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_remove_circle)
            return false
        }


        if (!phone.isNullOrBlank() && phone.length == 11) {
            requestBean?.coordinate_phone_number = phone
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)
        } else {
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_remove_circle)
            return false
        }


        if (!mobileNumber.isNullOrBlank() && mobileNumber.length == 11) {
            requestBean?.coordinate_mobile = mobileNumber
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)
        } else {
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_remove_circle)
            return false
        }


        if (!address.isNullOrBlank()) {
            requestBean?.address = address
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)
        } else {
            img_status.background = ContextCompat.getDrawable(context, R.drawable.ic_remove_circle)
            return false
        }

        return true
    }
}