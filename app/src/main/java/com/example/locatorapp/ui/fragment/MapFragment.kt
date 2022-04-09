package com.example.locatorapp.ui.fragment

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.locatorapp.R
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.ui.MainActivity
import com.example.locatorapp.util.Resource
import com.example.locatorapp.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment(R.layout.fragment_map) {

    lateinit var profileViewModel: ProfileViewModel
    lateinit var args: RequestBean
    //lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = (activity as MainActivity).profileViewModel

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


        btn_confirm.setOnClickListener {
            getData()
        }
        //getData()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getData()
    }

    private fun getData() {
        if (arguments != null) {
            args = arguments?.getParcelable("dataModel")!!

            val request = RequestBean(
                1,
                args.address,
                args.coordinate_mobile,
                args.coordinate_phone_number,
                args.first_name,
                args.gender,
                args.last_name,
                0.0,
                0.0
            )

            Log.e("getData===", request.address.toString())
            profileViewModel.getSaveAddressResponse(request)
            activity?.finish()
        }
    }

}