package com.example.syncaid.view.patient

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.syncaid.R
import com.example.syncaid.model.Faint
import com.example.syncaid.viewmodel.faintViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [patientHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class patientHistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val FaintViewModel = faintViewModel()
    var faintList = ArrayList<Faint>()



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


        val rootView = inflater.inflate(R.layout.fragment_patient_history, container, false)
        val recyclerview = rootView.findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(context)
        val adapter = faintAdapter(faintList)
        recyclerview.adapter = adapter


        val sharedPrefs = context?.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val id = sharedPrefs?.getString("ID","null")


        FaintViewModel.getFaints(id.toString()) { response, code ->
               Log.d("res",response.toString())
            if (code == 200) {

                faintList = Gson().fromJson(response.toString(), object : TypeToken<ArrayList<Faint>>() {}.type) as ArrayList<Faint>
                val adapter = faintAdapter(faintList)
                recyclerview.adapter = adapter


            } else {
                Log.e("ERROR", "Error: API call failed")

            }

        }


















        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment historyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            patientHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}