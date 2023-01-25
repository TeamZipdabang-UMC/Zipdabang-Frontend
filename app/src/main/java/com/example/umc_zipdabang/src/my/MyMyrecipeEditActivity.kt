package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMyMyrecipeEditBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyMyrecipeEditRVAdapter
import com.example.umc_zipdabang.src.my.etc.DeleteDialog

class MyMyrecipeEditActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyMyrecipeEditBinding
    private var scraps: ArrayList<ItemRecipeData> = arrayListOf()
    private var deleteList: ArrayList<ItemRecipeData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyMyrecipeEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.myCancelbtn.setOnClickListener {
            onBackPressed()
        }
        viewBinding.myToolbar.bringToFront()

        var intent = getIntent()
        scraps = ((intent.getSerializableExtra("array") as ArrayList<ItemRecipeData>?)!!)

        val adapter = MyMyrecipeEditRVAdapter(this, scraps)
        viewBinding.myRv.layoutManager = GridLayoutManager(this, 2)
        viewBinding.myRv.adapter = adapter

        adapter.setOnItemClickListener(object: MyMyrecipeEditRVAdapter.OnItemClickListener{
            override fun onItemClick(v: View?, pos: Int, tag: String) {
                if(tag == "1"){
                    deleteList.add(scraps[pos])
                }else{
                    deleteList.remove(scraps[pos])
                }

                if(deleteList.size == 0){
                    viewBinding.myTv.text="레시피 선택"
                    viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round)
                    viewBinding.myItemdeletebtn.setTextColor((ContextCompat.getColor(
                        applicationContext,R.color.jipdabang_login_tv_gray)))
                }else{
                    viewBinding.myTv.text = deleteList.size.toString() +"개의 레시피가 선택됨"
                    viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round_yellow)
                    viewBinding.myItemdeletebtn.setTextColor((ContextCompat.getColor(
                        applicationContext,R.color.jipdabang_black)))
                }
            }
        })

       val dialog = DeleteDialog(this)
        viewBinding.myItemdeletebtn.setOnClickListener{
            if(deleteList.isNotEmpty()){
                dialog.showDialog()
            }
        }
        dialog.setOnCLickListener(object: DeleteDialog.ButtonClickListener{
            override fun onClicked(){
                val a =deleteList.size
                for(i:Int in 0..a-1){
                    scraps.remove(deleteList[i])
                    adapter.notifyDataSetChanged()
                }

                deleteList.clear()
                viewBinding.myTv.text="레시피 선택"
                viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round)
                viewBinding.myItemdeletebtn.setTextColor((ContextCompat.getColor(
                        applicationContext,R.color.jipdabang_login_tv_gray)))
                viewBinding.myCancelbtn.text = "완료"
            }
        })
    }
}