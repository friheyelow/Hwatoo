package com.example.madcamp1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmations.adapter.ItemAdapter
import com.example.madcamp1.data.Datasource
import com.example.madcamp1.model.Imageres

class FragGallery : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val TAG: String = "로그"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FragGallery - onViewCreated() called")

        // Initialize data.
        val myDataset= Datasource().loadImageres()
        Log.d(TAG, "myDataset = $myDataset, size = ${myDataset.size}")

        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.recycler_view2)
        val iAdapter = ItemAdapter(requireContext(), myDataset)
        recyclerView?.adapter = iAdapter


        val gridLayoutManager=GridLayoutManager(requireContext(),3)
        //val staggeredGridLayoutManager=StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        recyclerView?.layoutManager=gridLayoutManager

        recyclerView?.setHasFixedSize(true)

        val luckyBtn: Button = requireView().findViewById<Button>(R.id.lucky_btn)

        luckyBtn.setOnClickListener {
            Log.d(TAG, "FragContact - onCreateView() called")

            var resultImage: Imageres =
                when ((1..33).random()) {
                    1 -> Imageres(R.drawable.ht1)
                    2 -> Imageres(R.drawable.ht2)
                    3 -> Imageres(R.drawable.ht3)
                    4 -> Imageres(R.drawable.ht4)
                    5 -> Imageres(R.drawable.ht5)
                    6 -> Imageres(R.drawable.ht6)
                    7 -> Imageres(R.drawable.ht7)
                    8 -> Imageres(R.drawable.ht8)
                    9 -> Imageres(R.drawable.ht9)
                    10 -> Imageres(R.drawable.ht10)
                    11 -> Imageres(R.drawable.ht11)
                    12 -> Imageres(R.drawable.ht12)
                    13 -> Imageres(R.drawable.ht13)
                    14 -> Imageres(R.drawable.ht14)
                    15 -> Imageres(R.drawable.ht15)
                    16 -> Imageres(R.drawable.ht16)
                    17 -> Imageres(R.drawable.ht17)
                    18 -> Imageres(R.drawable.ht18)
                    19 -> Imageres(R.drawable.ht19)
                    20 -> Imageres(R.drawable.ht20)
                    21 -> Imageres(R.drawable.ht21)
                    22 -> Imageres(R.drawable.ht22)
                    23 -> Imageres(R.drawable.ht23)
                    24 -> Imageres(R.drawable.ht24)
                    25 -> Imageres(R.drawable.ht25)
                    26 -> Imageres(R.drawable.ht26)
                    27 -> Imageres(R.drawable.ht27)
                    28 -> Imageres(R.drawable.ht28)
                    29 -> Imageres(R.drawable.ht29)
                    30 -> Imageres(R.drawable.ht30)
                    31 -> Imageres(R.drawable.ht31)
                    32 -> Imageres(R.drawable.ht32)
                    else -> Imageres(R.drawable.ht33)
                }

            if (!(resultImage in myDataset)){
                iAdapter.addImage(resultImage)
                iAdapter.notifyDataSetChanged()
                Toast.makeText(activity, "새로운 화투패를 발견했어요 🌸", Toast.LENGTH_SHORT).show()
                recyclerView?.smoothScrollToPosition(iAdapter.getSize())
            } else {
                Toast.makeText(activity, "아쉽지만 다음 기회에~🤪", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_gallery, container, false)
    }
}