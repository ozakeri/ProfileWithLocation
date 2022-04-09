package com.example.locatorapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.locatorapp.R
import com.example.locatorapp.model.RequestBean
import com.example.locatorapp.ui.MainActivity
import com.example.locatorapp.util.Resource
import com.example.locatorapp.viewmodel.ProfileViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_map.btn_confirm
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : Fragment() {

    lateinit var profileViewModel: ProfileViewModel
    lateinit var args: RequestBean
    lateinit var navController: NavController
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var isLoading = false

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnCameraChangeListener { cameraPosition ->

            latitude = cameraPosition.target.latitude
            longitude = cameraPosition.target.longitude

            Log.i("centerLat", latitude.toString())
            Log.i("centerLong", longitude.toString())
        }

        val center = CameraUpdateFactory.newLatLng(
            LatLng(
                35.739623, 51.411651
            )
        )

        val zoom = CameraUpdateFactory.zoomTo(18f)

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom)
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /*    val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.addMarker(MarkerOptions()
                .position(sydney)
                .draggable(true))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        profileViewModel = (activity as MainActivity).profileViewModel

        profileViewModel.saveAddressRepose.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { saveResponse ->
                        print("profileResponse====" + saveResponse.address)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })

        navController = Navigation.findNavController(view)

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
    }

    private fun getData() {
        if (arguments != null) {
            args = arguments?.getParcelable("dataModel")!!

            if (latitude != null && longitude != null) {
                val request = RequestBean(
                    1,
                    args.address,
                    args.coordinate_mobile,
                    args.coordinate_phone_number,
                    args.first_name,
                    args.gender,
                    args.last_name,
                    latitude,
                    longitude
                )

                Log.e("address===", request.address.toString())
                Log.e("lat===", request.lat.toString())
                Log.e("lng===", request.lng.toString())
                navController.navigate(R.id.listFragment)
                profileViewModel.getSaveAddressResponse(request)
            }
        }
    }

    private fun hideProgressBar() {
        progress_circular.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        progress_circular.visibility = View.VISIBLE
        isLoading = true
    }

}