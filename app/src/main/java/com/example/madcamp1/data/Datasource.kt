package com.example.madcamp1.data

import com.example.madcamp1.R
import com.example.madcamp1.model.Imageres

class Datasource() {
    fun loadImageres(num:Int): List<Imageres> {
        var imagelist= mutableListOf<Imageres>(
            Imageres(R.drawable.ht1),
            Imageres(R.drawable.ht2),
            Imageres(R.drawable.ht3),
            Imageres(R.drawable.ht4),
            Imageres(R.drawable.ht5),
            Imageres(R.drawable.ht6),
            Imageres(R.drawable.ht7),
            Imageres(R.drawable.ht8),
            Imageres(R.drawable.ht9),
            Imageres(R.drawable.ht10),
            Imageres(R.drawable.ht11),
            Imageres(R.drawable.ht12),
            Imageres(R.drawable.ht13),
            Imageres(R.drawable.ht14),
            Imageres(R.drawable.ht15),
            Imageres(R.drawable.ht16),
            Imageres(R.drawable.ht17),
            Imageres(R.drawable.ht18),
            Imageres(R.drawable.ht19),
            Imageres(R.drawable.ht20),
            Imageres(R.drawable.ht21),
            Imageres(R.drawable.ht22)
        )
        for(i in (21-num..21)) imagelist[i]=Imageres(R.drawable.pae0)
        return imagelist
    }
}