package com.example.locatorapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.locatorapp.R
import com.example.locatorapp.model.RequestBean
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment(R.layout.fragment_save) {

    private var gender: String = "Male"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController: NavController = Navigation.findNavController(view)

        fun mTextWatcher(img : ImageView) = object : TextWatcher {
            override fun afterTextChanged(et: Editable?) {

                if (TextUtils.isEmpty(et.toString().trim())) {
                    img.background =
                        ContextCompat.getDrawable(view.context, R.drawable.ic_remove_circle)
                } else {
                    img.background =
                        ContextCompat.getDrawable(view.context, R.drawable.ic_check_circle)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }

        edt_name.addTextChangedListener(mTextWatcher(img_status))
        edt_family.addTextChangedListener(mTextWatcher(img_status2))
        edt_mobileNumber.addTextChangedListener(mTextWatcher(img_status3))
        edt_phone.addTextChangedListener(mTextWatcher(img_status4))
        edt_address.addTextChangedListener(mTextWatcher(img_status5))

        radio_male.isChecked
        radio_male.setTextColor(resources.getColor(R.color.white))
        radio_female.setTextColor(resources.getColor(R.color.purple_500))

        toggle.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            val radio: RadioButton = group.findViewById(checkedId)

            when (radio.id) {
                R.id.radio_female -> {
                    gender = "Female"
                    radio_female.setTextColor(resources.getColor(R.color.white))
                    radio_male.setTextColor(resources.getColor(R.color.purple_500))
                }

                R.id.radio_male -> {
                    gender = "Male"
                    radio_male.setTextColor(resources.getColor(R.color.white))
                    radio_female.setTextColor(resources.getColor(R.color.purple_500))
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

            if (check) {
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


                val bundle = bundleOf("dataModel" to request)
                navController.navigate(R.id.mapsFragment, bundle);
            }
        }

        img_back.setOnClickListener {
            activity?.finish()
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
        } else {
            Toast.makeText(activity, getString(R.string.check_null_toast), Toast.LENGTH_LONG)
                .show()
            return false
        }


        if (!family.isNullOrBlank()) {
            requestBean?.last_name = family
        } else {
            Toast.makeText(activity, getString(R.string.check_null_toast), Toast.LENGTH_LONG)
                .show()
            return false
        }


        if (!phone.isNullOrBlank() && phone.length == 11) {
            requestBean?.coordinate_phone_number = phone
        } else {
            Toast.makeText(activity, getString(R.string.check_phone), Toast.LENGTH_LONG)
                .show()
            return false
        }


        if (!mobileNumber.isNullOrBlank() && mobileNumber.length == 11) {
            requestBean?.coordinate_mobile = mobileNumber
        } else {
            Toast.makeText(activity, getString(R.string.check_mobileNo), Toast.LENGTH_LONG)
                .show()
            return false
        }


        if (!address.isNullOrBlank()) {
            requestBean?.address = address
        } else {
            Toast.makeText(activity, getString(R.string.check_null_toast), Toast.LENGTH_LONG)
                .show()
            return false
        }

        return true
    }



}