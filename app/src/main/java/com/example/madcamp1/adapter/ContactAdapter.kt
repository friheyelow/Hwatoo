package com.example.madcamp1.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp1.R
import com.example.madcamp1.data.DataVo



class ContactAdapter (private val context: Context,
                      private val dataset: ArrayList<DataVo>
                      ): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var mPosition = 0
    val TAG: String = "로그"

    fun getPosition():Int {
        return mPosition
    }

    fun setPosition(position: Int){
        mPosition = position
    }

    fun addItem(dataVo: DataVo){
        dataset.add(dataVo)
        notifyDataSetChanged()
    }

    fun getSize():Int{
        if (dataset.size > 0){ return dataset.size-1}
        else{
            return 0
        }
    }

    fun removeItem(position: Int){
        if (dataset.size > 0){
            dataset.removeAt(position)
            Log.d(TAG, "ContactAdapter - removeItem() called")
            notifyDataSetChanged()
        }
    }




    class ContactViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val userPhoto = view.findViewById<ImageView>(R.id.contact_image)
        val nameView: TextView = view.findViewById(R.id.contact_name)
        val numberView: TextView = view.findViewById(R.id.contact_number)

        fun bind(dataVo: DataVo, context: Context){

//            if (dataVo.photo != "") {
//                val resourceId = context.resources.getIdentifier(dataVo.photo, "drawable", context.packageName)
//
//                if (resourceId > 0) {
//                    userPhoto.setImageResource(resourceId)
//                } else {
//                    userPhoto.setImageResource(R.mipmap.ic_launcher_round)
//                }
//            } else {
//                userPhoto.setImageResource(R.mipmap.ic_launcher_round)
//            }

            userPhoto.setImageResource(

                when ((1..5).random()) {
                    1 -> R.drawable.prof1
                    2 -> R.drawable.prof2
                    3 -> R.drawable.prof3
                    4 -> R.drawable.prof4
                    else -> R.drawable.prof5
                }
            )
            nameView.text = dataVo.name
            numberView.text = dataVo.number
        }

    }
    
    var alertLayoutInUse: View ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        Log.d(TAG, "ContactAdapter - onCreateViewHolder() called")
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_contact, parent, false)

        alertLayoutInUse = LayoutInflater.from(parent.context)
            .inflate(R.layout.delete_popup, parent, false)

        return ContactViewHolder(adapterLayout)
    }



    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
//        val contact = dataset[position]
//        holder.nameView.text = context.resources.getString(contact.NameResourceId)
//        holder.numberView.text = context.resources.getString(contact.NumberResourceId)


        holder.bind(dataset[position], context)
//        setAnimation(holder.itemView, position)

        holder.itemView.setOnClickListener {
            setPosition(position)
            Log.d(TAG, "ContactAdapter - onBindViewHolder() called")
            val context = holder.view.context
            val phoneNumber: String = dataset[position].number

            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
            context.startActivity(intent)
//            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener { view ->
            setPosition(position)
            fun showSettingPopup(){

                val popTextView: TextView = alertLayoutInUse!!.findViewById(R.id.pop_textview)
                popTextView.text="연락처를 삭제하면 되돌릴 수 없어요."

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("${dataset[position].name}님의 연락처를 삭제하시겠어요?")
                    .setPositiveButton("그래도 삭제하기") { _, _ ->
                        Toast.makeText(view.context, "${dataset[position].name}님의 연락처를 삭제했어요 👋", Toast.LENGTH_LONG).show()
                        dataset.removeAt(position)
                        notifyDataSetChanged()
                    }
                    .setNeutralButton("취소",null)
                    .create()
                alertDialog.setView(alertLayoutInUse)
                alertDialog.show()
            }

            showSettingPopup()

            return@setOnLongClickListener true
        }
    }


    //    private fun setAnimation(viewToAnimate: View, position: Int){
//        if (position > mPosition){
//            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
//            viewToAnimate.startAnimation(animation)
//        }
//    }
    override fun getItemCount(): Int {
        return dataset.size
    }


}





























