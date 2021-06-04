package com.ramonapp.android.weatherstack.today

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat.checkSelfPermission
import com.google.android.gms.location.*
import com.ramonapp.android.core.base.BaseFragment
import com.ramonapp.android.core.extension.ImageLoaderHelper
import com.ramonapp.android.core.extension.observe
import com.ramonapp.android.core.extension.showSnackBar
import com.ramonapp.android.core.repository.model.Current
import com.ramonapp.android.weatherstack.R
import kotlinx.android.synthetic.main.fragment_today.*
import org.koin.androidx.viewmodel.ext.android.viewModel


const val ARG_WEATHER_DATA = "weather_data"


class TodayFragment : BaseFragment() {

    private val todayViewModel: TodayViewModel by viewModel()
    private var weather: Current? = null

    override fun getResId() = R.layout.fragment_today

    override fun initView() {
         weather = arguments?.getParcelable(ARG_WEATHER_DATA)
    }

    override fun clickListeners() {

    }

    override fun subscribeViews() {

        todayViewModel.run {

            observe(weatherTypeLv) { weatherTypeTxt.text = it }
            observe(degreeLv) { degreeTxt.text = it }
            observe(feelLikeLv) { feelLikeTxt.text = it }
            observe(weatherImageLv) {
                ImageLoaderHelper.load(
                    context,
                    it,
                    weatherImg,
                    isCircle = true
                )
            }
            observe(windLv) { windTxt.text = it }
            observe(precipitationLv) { precipitationTxt.text = it }
            observe(visibilityLv) { visibilityTxt.text = it }
            observe(locationLv) { activity?.title = it }
            observe(todayViewModel.mError) {
                activity?.showSnackBar("Error")
            }
        }
    }

    override fun executeInitialTasks() {

    }

    override fun onResume() {
        super.onResume()
        weather?.let {
            todayViewModel.setViewParameter(it)
        } ?: run {
            if (todayViewModel.isLocationOn()) {
                getLastKnownLocation()
            } else {
                todayViewModel.getCurrentWeatherData()
            }
        }

    }

    private fun getLastKnownLocation() {
        context?.let { ctx ->
            if (checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val locationClient = LocationServices.getFusedLocationProviderClient(ctx)
                locationClient.lastLocation.addOnSuccessListener {
                    it?.run {
                        todayViewModel.getCurrentWeatherData("$latitude,$longitude")
                    } ?: run {
                        locationClient.requestLocationUpdates(
                            LocationRequest(), object : LocationCallback() {
                                override fun onLocationResult(p0: LocationResult) {
                                    super.onLocationResult(p0)
                                    p0.lastLocation.run {
                                        todayViewModel.getCurrentWeatherData("$latitude,$longitude")
                                    }
                                }
                            },
                            Looper.getMainLooper()
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(weather: Current? = null) = TodayFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_WEATHER_DATA, weather)
            }

        }
    }

}