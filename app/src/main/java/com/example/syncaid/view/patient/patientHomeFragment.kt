package com.example.syncaid.view.patient

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syncaid.R
import com.example.syncaid.databinding.FragmentPatientHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [patientHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class patientHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sleepCycle: BarChart
    private lateinit var heartRate: LineChart
    private lateinit var oxygenRate: LineChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_home, container, false)
        sleepCycle = view.findViewById(R.id.sleepcycle)
        heartRate = view.findViewById(R.id.heartrate)
        oxygenRate = view.findViewById(R.id.oxygenrate)

        sleepCycle.setDrawGridBackground(false)
        sleepCycle.setDrawBarShadow(false)
        sleepCycle.xAxis.setDrawAxisLine(false)
        sleepCycle.xAxis.setEnabled(false)
        sleepCycle.axisRight.setEnabled(false);
        sleepCycle.axisLeft.setEnabled(false);
        sleepCycle.description = null

        heartRate.setDrawGridBackground(false)
        heartRate.description = null
        heartRate.axisLeft.setDrawAxisLine(false)
        heartRate.axisRight.setDrawAxisLine(false)
        heartRate.xAxis.setDrawAxisLine(false)
        heartRate.xAxis.setEnabled(false)
        heartRate.axisRight.setEnabled(false);
        heartRate.axisLeft.setEnabled(false);

        oxygenRate.setDrawGridBackground(false)
        oxygenRate.axisLeft.setDrawAxisLine(false)
        oxygenRate.xAxis.setDrawAxisLine(false)
        oxygenRate.xAxis.setEnabled(false)
        oxygenRate.axisRight.setDrawAxisLine(false)

        oxygenRate.axisRight.setEnabled(false);
        oxygenRate.axisLeft.setEnabled(false);
        oxygenRate.description = null

        val heartratesEntries = listOf(
            Entry(0f, 60f),
            Entry(1f, 78f),
            Entry(2f, 85f),
            Entry(3f, 77f),
            Entry(4f, 80f),
            Entry(5f, 70f),
            Entry(6f, 81f),
            Entry(7f, 72f),
            Entry(8f, 68f),
            Entry(9f, 60f),
            Entry(10f, 60f),
        )

        val  OxygenRateEntries= listOf(
            Entry(0f, 5f),
            Entry(1f, 10f),
            Entry(2f, 10f),
            Entry(3f, 8f),
            Entry(4f, 6f),
            Entry(5f, 8f),
            Entry(6f, 10f),
            Entry(7f, 5f),
            Entry(8f, 9f),
            Entry(9f, 5f),
            Entry(10f, 3f),
        )
        val sleepCycleEntries = listOf(
            BarEntry(0f, 5f),
            BarEntry(1f, 5f),
            BarEntry(2f, 8f),
            BarEntry(3f, 7f),
            BarEntry(4f, 6f),
            BarEntry(5f, 7f),
            BarEntry(6f, 4f),
            BarEntry(7f, 9f),
        )

        val heartdataSet = LineDataSet(heartratesEntries, "")
        val heartlineData = LineData(heartdataSet)
        heartdataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        heartdataSet.color = Color.parseColor("#ee5253")
        heartdataSet.setCircleColor(Color.parseColor("#ee5253"))
        heartdataSet.valueTextSize = 10f
        heartdataSet.lineWidth = 3f
        heartdataSet.circleRadius = 4f


        val oxygendataSet = LineDataSet(OxygenRateEntries, "")
        val oxygenratedata = LineData(oxygendataSet)
        oxygendataSet.color = Color.parseColor("#2e86de")
        oxygendataSet.valueTextColor = Color.GRAY
        oxygendataSet.valueTextSize = 10f
        oxygendataSet.lineWidth = 3f
        oxygendataSet.circleRadius = 4f
        oxygendataSet.setDrawCircleHole(false)
        oxygendataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        oxygendataSet.setCircleColor(Color.parseColor("#2e86de"))
        oxygendataSet.setDrawHorizontalHighlightIndicator(false)


        val barDataSet = BarDataSet(sleepCycleEntries, "")
        val barData = BarData(barDataSet)
        barDataSet.valueTextSize = 10f
        barDataSet.color = Color.parseColor("#f1c40f")


        sleepCycle.data = barData
        sleepCycle.invalidate()

        heartRate.data = heartlineData
        heartRate.invalidate()
        oxygenRate.data = oxygenratedata
        oxygenRate.invalidate()


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            patientHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}