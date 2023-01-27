package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.databinding.ActivityMyFqaBinding

class MyFAQActivity : AppCompatActivity(){
    private lateinit var viewBinding: ActivityMyFqaBinding


    var faqlist : ArrayList<FAQ> = arrayListOf(
         FAQ(1,"무슨 메뉴가 맛있습니까", "안녕하세요. 집다방입니다. 오늘은 다름이 아니라 기필코 디자인을 다 끝내겠다는 저의 야무진 포부를 공지사항의 본문 내용을 넣겠습니다. 근데 이제 죙일 디자인만 하다보니 약간 제정신이 아닌 저의 멘탈의 곁들인.\n" +
                 "\n" + "굳이 답변을 하자면 액상과당은 배신을 하지 않습니만, 이상."),
        FAQ(1,"무슨 메뉴가 맛있습니까", "안녕하세요. 집다방입니다. 오늘은 다름이 아니라 기필코 디자인을 다 끝내겠다는 저의 야무진 포부를 공지사항의 본문 내용을 넣겠습니다. 근데 이제 죙일 디자인만 하다보니 약간 제정신이 아닌 저의 멘탈의 곁들인.\n" +
                "\n" + "굳이 답변을 하자면 액상과당은 배신을 하지 않습니만, 이상."),
                FAQ(1,"무슨 메뉴가 맛있습니까", "안녕하세요. 집다방입니다. 오늘은 다름이 아니라 기필코 디자인을 다 끝내겠다는 저의 야무진 포부를 공지사항의 본문 내용을 넣겠습니다. 근데 이제 죙일 디자인만 하다보니 약간 제정신이 아닌 저의 멘탈의 곁들인.\n" +
                "\n" + "굳이 답변을 하자면 액상과당은 배신을 하지 않습니만, 이상."),
    FAQ(1,"무슨 메뉴가 맛있습니까", "안녕하세요. 집다방입니다. 오늘은 다름이 아니라 기필코 디자인을 다 끝내겠다는 저의 야무진 포부를 공지사항의 본문 내용을 넣겠습니다. 근데 이제 죙일 디자인만 하다보니 약간 제정신이 아닌 저의 멘탈의 곁들인.\n" +
    "\n" + "굳이 답변을 하자면 액상과당은 배신을 하지 않습니만, 이상."),
        FAQ(1,"무슨 메뉴가 맛있습니까", "안녕하세요. 집다방입니다. 오늘은 다름이 아니라 기필코 디자인을 다 끝내겠다는 저의 야무진 포부를 공지사항의 본문 내용을 넣겠습니다. 근데 이제 죙일 디자인만 하다보니 약간 제정신이 아닌 저의 멘탈의 곁들인.\n" +
                "\n" + "굳이 답변을 하자면 액상과당은 배신을 하지 않습니만, 이상."))

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding =ActivityMyFqaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


         viewBinding.faqRv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val adapter= FaqAdatper(this, faqlist)
        viewBinding.faqRv.adapter=adapter
        adapter.setOnItemClickListener(object :FaqAdatper.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {

                //api 통신 필요  id배열 보내주면 intent로 id 전달/ detailactivity에서 id 받으면 백엔드로 id 보내줘서 상세 정보 받아와야함.
                val intent= Intent(applicationContext, NoticeDetailActivity::class.java)
                startActivity(intent)

            }


            })

                viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}
