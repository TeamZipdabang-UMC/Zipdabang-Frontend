package com.example.umc_zipdabang.src.main.zipdabang_recipe_comment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeBeverageBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBinding
import com.example.umc_zipdabang.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.BeverageRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.Comment
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.BeverageRecipesRVAdapter
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.RecipeDetailCommentRVAdapter
import kotlinx.coroutines.*
import java.lang.Runnable
import java.time.LocalDate
import java.time.LocalTime

class ZipdabangRecipeDetailCommentActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBinding

    private var isLoading = false
    var grid = 2
    val detailCommentList: ArrayList<Comment> = arrayListOf()

    private lateinit var recipeDetailCommentRVAdapter: RecipeDetailCommentRVAdapter

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailCommentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        val beverageRecipesList: ArrayList<BeverageRecipesData> = arrayListOf()
//        beverageRecipesList.apply {
//            // add(AllRecipesData(사진, 커피명, 좋아요 수)
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//        }
//
//        val beverageRecipesRVAdapter = BeverageRecipesRVAdapter(beverageRecipesList)
//
//        viewBinding.rvZipdabangRecipeBeverage.adapter = beverageRecipesRVAdapter
//        viewBinding.rvZipdabangRecipeBeverage.layoutManager = GridLayoutManager(this, 2)

        setData()
        initAdapter()
        initScrollListener()

        viewBinding.ivZipdabangRecipeDetailCommentsBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            finish()
        }
    }

    private fun setData() {
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )
        detailCommentList.add(
            Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
                "김기문", LocalDate.of(2020, 11, 23),
                LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
        )

    }


    private fun initAdapter() {
        recipeDetailCommentRVAdapter = RecipeDetailCommentRVAdapter(this, detailCommentList)
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvZipdabangRecipeDetailComment.setLayoutManager(layoutManager)
        viewBinding.rvZipdabangRecipeDetailComment.setAdapter(recipeDetailCommentRVAdapter)

        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                if (position == 0)
                {

                    return 1


                }
                else if ((position % 8 == 0) && position == (detailCommentList.size-1))
                {
                    return 2
                }
                else
                {

                    return 1
                }

            }
        })
    }

    private fun initScrollListener() {

        viewBinding.rvZipdabangRecipeDetailComment.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (viewBinding.rvZipdabangRecipeBeverage.layoutManager != null && (viewBinding.rvZipdabangRecipeBeverage.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (beverageRecipesList.size - 1)) {
                        //리스트 마지막o
                        moreItems()
                        isLoading = true

                    }
                }
            }
        })
    }


    private fun moreItems() {
        val runnable = Runnable {

            beverageRecipesList.add(BeverageRecipesData(null, null, null))

            Log.d("insert before","msg")

            beverageRecipesRVAdapter.notifyItemInserted(beverageRecipesList.size - 1)





        }
        viewBinding.rvZipdabangRecipeBeverage.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2 = Runnable {

                beverageRecipesList.removeAt(beverageRecipesList.size - 1)
                val scrollToPosition = beverageRecipesList.size
                beverageRecipesRVAdapter.notifyItemRemoved(scrollToPosition)


                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
                add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))


                beverageRecipesRVAdapter.notifyDataSetChanged()
                isLoading = false

            }
            runnable2.run()
        }
    }
}


//        val commentList: ArrayList<Comment> = arrayListOf()
//        commentList.apply {
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미asdfasdfasdfasdfa낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니asdfasdfasdfasdf아ㅓ미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어asdfasdfasdfazxcvxzdfaeawdf요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓasdfasdvcdawefasdcvadawe미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이xzcasedfwaefasdㅏㄻ니아ㅓ미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁasfdddddasdfacewaefcwcawertㄴ이ㅏㄻ니아ㅓ미낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미vxzdfaeae낭머"))
//            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁaweraqdfavcasdfaqewfㄴ이ㅏㄻ니아ㅓ미낭머"))