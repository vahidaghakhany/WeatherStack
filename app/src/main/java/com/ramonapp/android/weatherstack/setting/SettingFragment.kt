package com.ramonapp.android.weatherstack.setting

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.ramonapp.android.core.SharedPreferenceManager
import com.ramonapp.android.core.UnitType
import com.ramonapp.android.core.base.BaseFragment
import com.ramonapp.android.weatherstack.R
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SettingFragment : BaseFragment() {

    private val settingViewModel: SettingViewModel by viewModel()

    override fun getResId() = R.layout.fragment_setting

    override fun initView() {
        deviceLocationSw.isChecked = settingViewModel.isLocationOn()
    }

    override fun clickListeners() {
        setUnitTxt.setOnClickListener {
            context?.let { showUnitDialog(it) }
        }

        setLocationTxt.setOnClickListener {
            context?.let { showLocationDialog(it) }
        }

        deviceLocationSw.setOnCheckedChangeListener { _, isOn ->

            if (isOn) {
                settingViewModel.setLocationState(true)
                context?.let { ctx ->
                    if (checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION),
                            10
                        )
                    }
                }
            } else {
                settingViewModel.setLocationState(false)
            }
        }
    }

    override fun subscribeViews() {
    }

    override fun executeInitialTasks() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            deviceLocationSw.isChecked = false
        }
    }

    private fun showUnitDialog(context: Context) {
        val units = UnitType.values().map { it.name }.toTypedArray()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle("Unit system")
            .setSingleChoiceItems(units, settingViewModel.getSelectedUnitIndex(),
                DialogInterface.OnClickListener { dialog, which ->
                    settingViewModel.setSelectedUnit(units[which])
                })
            .setNegativeButton("Close", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showLocationDialog(context: Context) {

        val input = EditText(context).apply {

        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle("Location")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener { _, _ ->
                    settingViewModel.setLocation(input.text.toString())
                    settingViewModel.setLocationState(false)
                    deviceLocationSw.isChecked = false
                })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object {
        fun newInstance() = SettingFragment()
    }

}