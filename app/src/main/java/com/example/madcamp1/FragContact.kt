package com.example.madcamp1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp1.adapter.ContactAdapter
import com.example.madcamp1.data.DataVo
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class FragContact : Fragment() {
    val TAG: String = "로그"
    @SuppressLint("SetJavaScriptEnabled")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "FragContact - onCreate() called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FragContact - onViewCreated() called")
        var imm : InputMethodManager? = null
        imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        var userList: ArrayList<DataVo> = ArrayList()
        try {
            val inputStream = context?.assets?.open("ContactList.json")
            val size = inputStream!!.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val strJson = String(buffer, Charsets.UTF_8)

            val jsonObject = JSONObject(strJson)

            // 파일 읽어오는 IO 처리 부분 메서드 분기 처리방법
//            val jsonObject2 = JSONObject(loadJSONFileFromAsset())
//            Log.d(TAG, "jsonObject2:$jsonObject2")

            val userArray = jsonObject.getJSONArray("contacts")
            Log.d(TAG, "userArray:$userArray")
            for (i in 0 until userArray.length()) {
                Log.d(TAG, "for $i in 0 until userArray.length")
                val baseInfo = userArray.getJSONObject(i)
                Log.d(TAG, "baseInfo:$baseInfo")

                val tempData = DataVo(
                    baseInfo.getString("name"),
                    baseInfo.getString("id"),
                    baseInfo.getString("number")
                )
                userList.add(tempData)
                Log.d(TAG, "userList:$userList")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
        val mAdapter = ContactAdapter(requireContext(), userList)
        recyclerView.adapter = mAdapter

        fun hideKeyboard(view: View) {
            Log.d(TAG, "FragContact - hideKeyboard() called")
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Log.d(TAG, "mAdapter=$mAdapter")
        val layout = LinearLayoutManager(activity)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)
        val addBtn = getView()?.findViewById<Button>(R.id.add_btn)
        // 하이픈
        val etNumber = requireView().findViewById<EditText>(R.id.input_number)
        etNumber.setRawInputType(android.text.InputType.TYPE_CLASS_PHONE)
        etNumber.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        val etName = requireView().findViewById<EditText>(R.id.input_name)
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(TAG, "FragContact - onTextChanged() called")
                if (etName.length()!=0) {
                    if (etNumber?.length()==13){
                        Log.d(TAG, "MainActivity - onTextChanged() called, number length = 13, namecheck: ${etNumber.length()!=0}")
                        addBtn?.isClickable = true
                        addBtn?.isEnabled = true
                    }
                }
            }
        })
        etNumber?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(TAG, "FragContact - onTextChanged() called")
                if (etNumber.length()< 13) {
                    Log.d(TAG, "MainActivity - onTextChanged() called, length=${etNumber.length()}, namecheck: ${etName?.length()!=0}")
                    addBtn?.isClickable = false
                    addBtn?.isEnabled = false
                } else if (etName?.length()!=0) {
                    Log.d(TAG, "MainActivity - onTextChanged() called, length=${etNumber.length()}, namecheck: ${etName?.length()!=0}")
                    addBtn?.isClickable = true
                    addBtn?.isEnabled = true
                }
            }
        })
        addBtn?.setOnClickListener {
            Log.d(TAG, "FragContact - onCreateView() called")
            hideKeyboard(it)
            val inputName = etName.text.toString()
            val inputNumber = etNumber.text.toString()
            Log.d(TAG, "inputname: $inputName")
            Log.d(TAG, "inputnumb: $inputNumber")
            mAdapter.addItem(DataVo(inputName,"test${mAdapter.getPosition()}",inputNumber))
            mAdapter.notifyDataSetChanged()
            Log.d(TAG, "MainActivity - onCreate() called, addbtn, ${mAdapter.getPosition()}")
            Toast.makeText(activity, "연락처가 등록되었어요 🥳", Toast.LENGTH_SHORT).show()
            recyclerView?.smoothScrollToPosition(mAdapter.getSize())


            etNumber?.text = null
            etName?.text = null

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_contact, container, false)
    }

}
