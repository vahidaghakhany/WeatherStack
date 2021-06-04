package com.ramonapp.android.weatherstack.sevendays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramonapp.android.core.UnitType
import com.ramonapp.android.core.extension.ImageLoaderHelper
import com.ramonapp.android.core.repository.model.Current
import com.ramonapp.android.weatherstack.R
import kotlinx.android.synthetic.main.days_row.view.*


class DaysAdapter(
    private var mDaysList: List<Current>,
    private val unit: UnitType,
    private val clicked: ((current:Current?) -> Unit)

) : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.days_row, parent, false)

        view.setOnClickListener {
            (view.tag as? Int)?.let { position ->
                clicked(mDaysList.getOrNull(position))
            }
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(mDaysList[position], position)

    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(weather: Current, position: Int) {

            ImageLoaderHelper.load(
                view.context,
                weather.weatherIcons?.getOrNull(0),
                view.iconImg,
                isCircle = true,
            )
            view.timeTxt.text = weather.observationTime
            view.degreeTxt.text = "${weather.temperature} ° ${if (unit == UnitType.Imperial) "F" else "C"}"
            view.weatherTypeTxt.text = weather.weatherDescriptions?.joinToString()
            view.tag = position
        }
    }

    override fun getItemCount() = mDaysList.size
}