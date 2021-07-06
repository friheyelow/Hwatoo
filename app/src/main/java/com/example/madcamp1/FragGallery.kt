package com.example.madcamp1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.adapter.ItemAdapter
import com.example.madcamp1.data.Datasource

class FragGallery : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val TAG: String = "로그"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FragGallery - onViewCreated() called")

        // Initialize data.
        val myDataset = Datasource().loadImageres()

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter = ItemAdapter(requireContext(), myDataset)

        val gridLayoutManager= GridLayoutManager(requireContext(),3)
        //val staggeredGridLayoutManager=StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        recyclerView?.layoutManager=gridLayoutManager

        recyclerView?.setHasFixedSize(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_gallery, container, false)
    }
}