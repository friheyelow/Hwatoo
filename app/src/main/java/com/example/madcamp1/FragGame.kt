package com.example.hwatoo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.madcamp1.FragGallery
import com.example.madcamp1.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragGame : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.
        inflate(R.layout.frag_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawButton: Button = requireView().findViewById(R.id.drawButton)
        val halfButton: Button = requireView().findViewById(R.id.halfbutton)
        val doubleButton: Button =requireView().findViewById(R.id.doublebutton)
        val moneyView1: TextView =requireView().findViewById(R.id.moneyView1)   //computer 판돈 보여주기
        val moneyView2: TextView =requireView().findViewById(R.id.moneyView2)   //player 판돈 보여주기
        val computerwinlose: ImageView=requireView().findViewById(R.id.computerwinlose)
        val playerwinlose: ImageView=requireView().findViewById(R.id.playerwinlose)
        val regamebutton: Button =requireView().findViewById(R.id.regamebutton)
        val diebutton: Button =requireView().findViewById(R.id.diebutton)

        regamebutton.isEnabled=false
        computerwinlose.visibility=View.GONE
        playerwinlose.visibility=View.GONE

        val computermoneyText: TextView=requireView().findViewById(R.id.computermoneyText)
        val playermoneyText: TextView=requireView().findViewById(R.id.playermoneyText)
        var gameinfo=Gameinfo()

        val callbutton: Button=requireView().findViewById(R.id.callButton)
        callbutton.isEnabled=false
        computermoneyText.text=gameinfo.computermoney.toString()
        playermoneyText.text=gameinfo.playermoney.toString()
        halfButton.isEnabled=false
        diebutton.isEnabled=false

        doubleButton.isEnabled=false
        // 갤러리로 값 넘기기
        val bundle=Bundle()
        bundle.putInt("num",3)
        FragGallery().arguments=bundle
        activity?.supportFragmentManager!!.beginTransaction().commit()

        diebutton.setOnClickListener{
            gameinfo.computermoney+=gameinfo.playerstake
            gameinfo.playermoney-=gameinfo.playerstake
            computerwinlose.visibility=View.VISIBLE
            playerwinlose.visibility=View.VISIBLE
            computerwinlose.setImageResource(R.drawable.win_removebg_preview)
            playerwinlose.setImageResource(R.drawable.lose_removebg_preview)
            updateview(gameinfo,0)
            gameinfo.computerstake=100
            gameinfo.playerstake=100
            gameinfo.playerpae.clear()
            gameinfo.computerpae.clear()
            gameinfo.turn=1
            gameinfo.pae.paelist.clear()
            gameinfo.playerrank=0
            gameinfo.computerrank=0

            callbutton.isEnabled=false
            halfButton.isEnabled=false
            doubleButton.isEnabled=false
            diebutton.isEnabled=false
            regamebutton.isEnabled=true
        }
        regamebutton.setOnClickListener{
            if(gameinfo.computermoney<=0){
                val bundle=Bundle()
                bundle.putInt("num",1)
                setFragmentResult("key",bundle)
                gameinfo= Gameinfo()
            }
            else if(gameinfo.playermoney<=0) gameinfo=Gameinfo()
            drawButton.isEnabled=true
            regamebutton.isEnabled=false
            computerwinlose.visibility=View.GONE
            playerwinlose.visibility=View.GONE
            updateview(gameinfo,1)
        }

        drawButton.setOnClickListener {
            halfButton.isEnabled=true
            doubleButton.isEnabled=true
            drawButton.isEnabled=false
            callbutton.isEnabled=true
            regamebutton.isEnabled=false
            diebutton.isEnabled=true
            drawpae(gameinfo,gameinfo.turn)
            moneyView1.text=gameinfo.computerstake.toString()
            moneyView2.text=gameinfo.playerstake.toString()
        }
        callbutton.setOnClickListener{
            //gameinfo.playermoney-=(gameinfo.computerstake-gameinfo.playerstake)
            moneyView2.text=gameinfo.playerstake.toString()
            if(gameinfo.computerstake==gameinfo.playerstake) calcomputerstake(gameinfo)
            else if(gameinfo.turn==2) {
                gameinfo.playerstake=gameinfo.computerstake
                drawpae(gameinfo, gameinfo.turn)
                moneyView1.text=gameinfo.computerstake.toString()
                moneyView2.text=gameinfo.playerstake.toString()
            }
            else {
                gameinfo.playerstake = gameinfo.computerstake
                rankcal(gameinfo)
                comparepae(gameinfo)
                updateview(gameinfo, 0)
                gameinfo.computerstake = 100
                gameinfo.playerstake = 100
                gameinfo.playerpae.clear()
                gameinfo.computerpae.clear()
                gameinfo.turn = 1
                gameinfo.pae.paelist.clear()
                gameinfo.playerrank = 0
                gameinfo.computerrank = 0
                callbutton.isEnabled = false
                halfButton.isEnabled = false
                diebutton.isEnabled = false
                doubleButton.isEnabled = false
                regamebutton.isEnabled = true
            }
        }
        halfButton.setOnClickListener{
            gameinfo.playerstake=gameinfo.computerstake*3/2
            moneyView2.text=gameinfo.playerstake.toString()
            calcomputerstake(gameinfo)
        }
        doubleButton.setOnClickListener{
            gameinfo.playerstake=gameinfo.computerstake*2
            moneyView2.text=gameinfo.playerstake.toString()
            calcomputerstake(gameinfo)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun comparepae(gameinfo: Gameinfo){
        val computerwinlose: ImageView=requireView().findViewById(R.id.computerwinlose)
        val playerwinlose: ImageView=requireView().findViewById(R.id.playerwinlose)
        computerwinlose.visibility=View.VISIBLE
        playerwinlose.visibility=View.VISIBLE
        if(gameinfo.computerrank>=gameinfo.playerrank){
            gameinfo.computermoney+=gameinfo.playerstake
            gameinfo.playermoney-=gameinfo.computerstake
            computerwinlose.setImageResource(R.drawable.win_removebg_preview)
            playerwinlose.setImageResource(R.drawable.lose_removebg_preview)
        }
        else if(gameinfo.computerrank<gameinfo.playerrank){
            gameinfo.computermoney-=gameinfo.playerstake
            gameinfo.playermoney+=gameinfo.computerstake
            computerwinlose.setImageResource(R.drawable.lose_removebg_preview)
            playerwinlose.setImageResource(R.drawable.win_removebg_preview)
        }
    }
    private fun drawpae(gameinfo: Gameinfo,turn: Int) {
        // Create new Dice object with 6 sides and roll it
        if(turn>2){
            Toast.makeText(activity,"패는 2장까지 입니다",Toast.LENGTH_SHORT).show()
        }
        if(turn==1) {
            val paenum1 = gameinfo.pae.draw()
            val paenum2 = gameinfo.pae.draw()
            gameinfo.computerpae.add(paenum1)  //컴퓨터 패 추가
            gameinfo.playerpae.add(paenum2)   //플레이어 패 추가
            val paeImage1: ImageView = requireView().findViewById(R.id.computerpae1)
            val paeImage2: ImageView = requireView().findViewById(R.id.playerpae1)
            //적용
            paeImage1.setImageResource(R.drawable.pae0)
            paeImage2.setImageResource(returnpaeimage(paenum2))
            gameinfo.turn+=1
        }
        if(turn==2) {
            val paenum1 = gameinfo.pae.draw()
            val paenum2 = gameinfo.pae.draw()
            gameinfo.computerpae.add(paenum1)  //컴퓨터 패 추가
            gameinfo.playerpae.add(paenum2)   //플레이어 패 추가
            val paeImage1: ImageView = requireView().findViewById(R.id.computerpae2)
            val paeImage2: ImageView = requireView().findViewById(R.id.playerpae2)
            //적용
            paeImage1.setImageResource(R.drawable.pae0)
            paeImage2.setImageResource(returnpaeimage(paenum2))
            gameinfo.turn+=1
        }
        // Update the content description
        //paeImage.contentDescription = drawpae().toString()
    }
    fun calcomputerstake(gameinfo: Gameinfo){
        val halfButton: Button = requireView().findViewById(R.id.halfbutton)
        val doubleButton: Button =requireView().findViewById(R.id.doublebutton)
        val moneyView1: TextView =requireView().findViewById(R.id.moneyView1)   //computer 판돈 보여주기
        val regamebutton: Button =requireView().findViewById(R.id.regamebutton)
        val callbutton: Button =requireView().findViewById(R.id.callButton)
        val diebutton: Button =requireView().findViewById(R.id.diebutton)
        val computerwinlose: ImageView=requireView().findViewById(R.id.computerwinlose)
        val playerwinlose: ImageView=requireView().findViewById(R.id.playerwinlose)
        val computeradd=(1..10).random()
        var dc=2 //die call
        var ch=6 //call half
        var hd=8 //half double
        if(gameinfo.turn==2){ //패 1장일때 확률
            if(gameinfo.computerpae[0]==1 || gameinfo.computerpae[0]==2){ //1
                dc=0
                ch=2
                hd=5
            }
            else if(gameinfo.computerpae[0]==7 || gameinfo.computerpae[0]==8){ //4
                dc=0
                ch=3
                hd=6
            }
            else if(gameinfo.computerpae[0]==7 || gameinfo.computerpae[0]==8){ //10
                dc=0
                ch=3
                hd=7
            }
            else if(gameinfo.computerpae[0]==7 || gameinfo.computerpae[0]==8){ // 2 6 9
                dc=1
                ch=4
                hd=7
            }
        }
        if(gameinfo.turn==3){ //패 2장일때 확률
            rankcal(gameinfo)
            if(gameinfo.computerrank>=100){ //광땡 땡
                dc=0
                ch=1
                hd=4
            }
            else if(gameinfo.computerrank>=10){
                dc=0
                ch=4
                hd=7
            }
        }
        if(computeradd in 0..dc){ //die
            Toast.makeText(activity,"Computer die!",Toast.LENGTH_SHORT).show()
            gameinfo.computermoney-=gameinfo.playerstake
            gameinfo.playermoney+=gameinfo.playerstake
            computerwinlose.visibility=View.VISIBLE
            playerwinlose.visibility=View.VISIBLE
            computerwinlose.setImageResource(R.drawable.lose_removebg_preview)
            playerwinlose.setImageResource(R.drawable.win_removebg_preview)
            updateview(gameinfo,0)
            gameinfo.computerstake=100
            gameinfo.playerstake=100
            gameinfo.playerpae.clear()
            gameinfo.computerpae.clear()
            gameinfo.turn=1
            gameinfo.pae.paelist.clear()
            gameinfo.playerrank=0
            gameinfo.computerrank=0

            callbutton.isEnabled=false
            halfButton.isEnabled=false
            doubleButton.isEnabled=false
            diebutton.isEnabled=false
            regamebutton.isEnabled=true
        }
        else if(computeradd in dc+1..ch){ //call
            Toast.makeText(activity,"Computer call!",Toast.LENGTH_SHORT).show()
            if(gameinfo.turn==2) {
                gameinfo.computerstake=gameinfo.playerstake
                drawpae(gameinfo, gameinfo.turn)
                moneyView1.text=gameinfo.computerstake.toString()
            }
            else {
                gameinfo.computerstake=gameinfo.playerstake
                rankcal(gameinfo)
                comparepae(gameinfo)
                updateview(gameinfo,0)
                gameinfo.computerstake=100
                gameinfo.playerstake=100
                gameinfo.playerpae.clear()
                gameinfo.computerpae.clear()
                gameinfo.turn=1
                gameinfo.pae.paelist.clear()
                gameinfo.playerrank=0
                gameinfo.computerrank=0
                diebutton.isEnabled=false
                callbutton.isEnabled=false
                halfButton.isEnabled=false
                doubleButton.isEnabled=false
                regamebutton.isEnabled=true
            }
        }
        else if(computeradd in ch+1..hd){ //half
            Toast.makeText(activity,"Computer half",Toast.LENGTH_SHORT).show()
            gameinfo.computerstake=gameinfo.playerstake*3/2
            moneyView1.text=gameinfo.computerstake.toString()
        }
        else if(computeradd in hd+1..10){ //double
            Toast.makeText(activity,"Computer double",Toast.LENGTH_SHORT).show()
            gameinfo.computerstake=gameinfo.playerstake*2
            moneyView1.text=gameinfo.computerstake.toString()
        }
    }
    private fun updateview(gameinfo: Gameinfo,regame:Int){
        val paeImage1: ImageView = requireView().findViewById(R.id.computerpae1)
        val paeImage2: ImageView = requireView().findViewById(R.id.computerpae2)
        val paeImage3: ImageView = requireView().findViewById(R.id.playerpae1)
        val paeImage4: ImageView = requireView().findViewById(R.id.playerpae2)
        val moneyView1: TextView =requireView().findViewById(R.id.moneyView1)
        val moneyView2: TextView =requireView().findViewById(R.id.moneyView2)   //판돈 보여주기

        val computermoneyText: TextView=requireView().findViewById(R.id.computermoneyText)
        val playermoneyText: TextView=requireView().findViewById(R.id.playermoneyText)
        moneyView1.text=gameinfo.computerstake.toString()
        moneyView2.text=gameinfo.playerstake.toString()
        computermoneyText.text=gameinfo.computermoney.toString()
        playermoneyText.text=gameinfo.playermoney.toString()
        if(regame==1){
            paeImage1.setImageResource(0)
            paeImage2.setImageResource(0)
            paeImage3.setImageResource(0)
            paeImage4.setImageResource(0)
        }
        if(gameinfo.turn>=3){
            paeImage1.setImageResource(returnpaeimage(gameinfo.computerpae[0]))
            paeImage2.setImageResource(returnpaeimage(gameinfo.computerpae[1]))
        }
    }
    private fun returnpaeimage(num: Int):Int{
        return when (num) {
            1 -> R.drawable.pae1_1
            2 -> R.drawable.pae1_2
            3 -> R.drawable.pae2_1
            4 -> R.drawable.pae2_2
            5 -> R.drawable.pae3_1
            6 -> R.drawable.pae3_2
            7 -> R.drawable.pae4_1
            8 -> R.drawable.pae4_2
            9 -> R.drawable.pae5_1
            10 -> R.drawable.pae5_2
            11 -> R.drawable.pae6_1
            12 -> R.drawable.pae6_2
            13 -> R.drawable.pae7_1
            14 -> R.drawable.pae7_2
            15 -> R.drawable.pae8_1
            16 -> R.drawable.pae8_2
            17 -> R.drawable.pae9_1
            18 -> R.drawable.pae9_2
            19 -> R.drawable.pae10_1
            20 -> R.drawable.pae10_2
            else -> R.drawable.pae1_1
        }
    }
    private fun rankcal(gameinfo: Gameinfo){
        val computerae1=(gameinfo.computerpae[0]+1)/2
        val computerae2=(gameinfo.computerpae[1]+1)/2
        val playerpae1=(gameinfo.playerpae[0]+1)/2
        val playerpae2=(gameinfo.playerpae[1]+1)/2

        if((gameinfo.computerpae[0]==5 && gameinfo.computerpae[1]==15)||(gameinfo.computerpae[0]==15 && gameinfo.computerpae[1]==5)) gameinfo.computerrank=5000  //38 광땡
        else if((gameinfo.computerpae[0]==1 && gameinfo.computerpae[1]==5)||(gameinfo.computerpae[0]==5 && gameinfo.computerpae[1]==1)) gameinfo.computerrank=4000  //13 광땡
        else if((gameinfo.computerpae[0]==1 && gameinfo.computerpae[1]==15)||(gameinfo.computerpae[0]==15 && gameinfo.computerpae[1]==1)) gameinfo.computerrank=3000  //18 광땡
        else if(computerae1==computerae2) gameinfo.computerrank=computerae1*100 //땡
        else if((computerae1==1 && computerae2==2) || (computerae1==2 && computerae2==1)) gameinfo.computerrank=90 //알리
        else if((computerae1==1 && computerae2==4) || (computerae1==4 && computerae2==1)) gameinfo.computerrank=80 //독사
        else if((computerae1==1 && computerae2==9) || (computerae1==9 && computerae2==1)) gameinfo.computerrank=70 //구삥
        else if((computerae1==1 && computerae2==10) || (computerae1==10 && computerae2==1)) gameinfo.computerrank=60 //장삥
        else if((computerae1==4 && computerae2==10) || (computerae1==10 && computerae2==4)) gameinfo.computerrank=50 //장사
        else if((computerae1==4 && computerae2==6) || (computerae1==6 && computerae2==4)) gameinfo.computerrank=40 //세륙
        else{
            gameinfo.computerrank=(computerae1+computerae2)%10
        }

        if((gameinfo.playerpae[0]==5 && gameinfo.playerpae[1]==15)||(gameinfo.playerpae[0]==15 && gameinfo.playerpae[1]==5)) gameinfo.playerrank=5000  //38 광땡
        else if((gameinfo.playerpae[0]==1 && gameinfo.playerpae[1]==5)||(gameinfo.playerpae[0]==5 && gameinfo.playerpae[1]==1)) gameinfo.playerrank=4000  //13 광땡
        else if((gameinfo.playerpae[0]==1 && gameinfo.playerpae[1]==15)||(gameinfo.playerpae[0]==15 && gameinfo.playerpae[1]==1)) gameinfo.playerrank=3000  //18 광땡
        else if(playerpae1==playerpae2) gameinfo.playerrank=playerpae1*100 //땡
        else if((playerpae1==1 && playerpae2==2) || (playerpae1==2 && playerpae2==1)) gameinfo.playerrank=90 //알리
        else if((playerpae1==1 && playerpae2==4) || (playerpae1==4 && playerpae2==1)) gameinfo.playerrank=80 //독사
        else if((playerpae1==1 && playerpae2==9) || (playerpae1==9 && playerpae2==1)) gameinfo.playerrank=70 //구삥
        else if((playerpae1==1 && playerpae2==10) || (playerpae1==10 && playerpae2==1)) gameinfo.playerrank=60 //장삥
        else if((playerpae1==4 && playerpae2==10) || (playerpae1==10 && playerpae2==4)) gameinfo.playerrank=50 //장사
        else if((playerpae1==4 && playerpae2==6) || (playerpae1==6 && playerpae2==4)) gameinfo.playerrank=40 //세륙
        else{
            gameinfo.playerrank=(playerpae1+playerpae2)%10
        }
    }

}

class Pae(){
    val paelist= mutableListOf<Int>()

    fun draw(): Int{
        var a= (1..20).random()
        while(paelist.contains(a)){
            a=(1..20).random()
        }
        paelist.add(a)
        return a
    }
}


class Gameinfo(){
    val pae=Pae()
    var computerpae= mutableListOf<Int>()
    var playerpae= mutableListOf<Int>()
    var computerrank=0
    var playerrank=0
    var turn=1 // 첫패인지 두번째 패인지 1 or 2의 val
    var playerstake=100
    var computerstake=100
    var playermoney=5000
    var computermoney=5000
}