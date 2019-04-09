package com.example.nuber


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_nuber_shopping.*



// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NuberShoppingFragment : Fragment() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var lastLocation: Location


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuber_shopping, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        getLocation()
        super.onActivityCreated(savedInstanceState)
        send_salad_button.setOnClickListener {
            val  _db = FirebaseDatabase.getInstance().getReference("salands")
            val k = _db.push().key
            val s = Salad(salad_name_edittext.text.toString(),
                salad_description_edittext.text.toString(),salad_price.text.toString() ,lastLocation,  k.toString())


            _db.child(k!!).setValue(s).addOnCompleteListener {
                Toast.makeText(activity, "Your purchase has been placed", Toast.LENGTH_LONG).show()


            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocation(){


        lastLocation.latitude = 0.0//your coords of course
        lastLocation.longitude = 0.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)


        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            if (location != null) {
                lastLocation = location

            } else {
                lastLocation.latitude = 0.0//your coords of course
                lastLocation.longitude = 0.0
            }


        }
    }


}
