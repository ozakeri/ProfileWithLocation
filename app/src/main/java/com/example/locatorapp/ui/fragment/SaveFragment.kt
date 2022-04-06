package com.example.locatorapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.locatorapp.R
import com.example.locatorapp.model.RequestBean
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment(R.layout.fragment_save) {


    private var gender: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController: NavController = Navigation.findNavController(view)


        toggle.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            val radio: RadioButton = group.findViewById(checkedId)

            when (radio.id) {
                R.id.radio_female -> {
                    gender = "Female"
                }

                R.id.radio_male -> {
                    gender = "Male"
                }
            }

            Log.e("selectedtext-->", radio.text.toString())

        })

        btn_save.setOnClickListener {

            val name = edt_name?.text.toString().trim()
            val family = edt_family?.text.toString().trim()
            val phone = edt_phone?.text.toString().trim()
            val mobileNumber = edt_mobileNumber?.text.toString().trim()
            val address = edt_address?.text.toString().trim()
            val context = view.context

            val check = chekInput(context, name, family, phone, mobileNumber, address)

           // if (check) {
                val request = RequestBean(
                    1,
                    address,
                    mobileNumber,
                    phone,
                    name,
                    gender,
                    family,
                    0.0,
                    0.0
                )
                //

                val bundle = bundleOf("dataModel" to request)
                navController.navigate(R.id.mapFragment, bundle);
           // }
        }
    }


    fun chekInput(
        context: Context,
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