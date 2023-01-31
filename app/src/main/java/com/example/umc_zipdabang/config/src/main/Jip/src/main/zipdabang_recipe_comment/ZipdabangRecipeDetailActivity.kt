package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.Token
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.IngredientsRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.RecipeDetailCommentRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.RecipeOrderRVAdapter
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.databinding.RecipeSuccessDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class ZipdabangRecipeDetailActivity: AppCompatActivity() {
    private lateinit var dialogBinding: RecipeSuccessDialogBinding
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2
    // 사용자에 따라 달라짐
    private var like: Boolean? = false
    private var scrap: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        // 인텐트 가져오기
        val selectedRecipeId = intent.getStringExtra("recipeId")
        Log.d("선택된 레시피의 Id", "${selectedRecipeId}")
        val idInt = selectedRecipeId?.toInt()

        var mainImageUrl: String? = ""

        // 리사이클러뷰에 활용할 배열 및 자료구조들

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)

//        var mainImageUrl: String? = ""

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
            finish()
        }
        // 다시하기

        viewBinding.ivZipdabangRecipeLike.setOnClickListener {
            if (!like!!) {
                like = true
                viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_filled)
                var newLikeNum = viewBinding.tvZipdabangRecipeLikeNum.text.toString().toInt() + 1
                viewBinding.tvZipdabangRecipeLikeNum.setText(newLikeNum.toString())

                showLikeToast()
            } else {
                like = false
                viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)
                var newLikeNum = viewBinding.tvZipdabangRecipeLikeNum.text.toString().toInt() - 1
                viewBinding.tvZipdabangRecipeLikeNum.setText(newLikeNum.toString())
                showLikeCancelToast()
            }
            GlobalScope.launch(Dispatchers.IO) {
                val recipeId = idInt
                val token: Token = tokenDb.tokenDao().getToken()
                recipeService.pressLike(token.token, recipeId).enqueue(object: Callback<PressLikeResponse> {
                    override fun onResponse(
                        call: Call<PressLikeResponse>,
                        response: Response<PressLikeResponse>
                    ) {
                        Log.d("좋아요 누르기 성공", "${response.body()}")
                    }

                    override fun onFailure(call: Call<PressLikeResponse>, t: Throwable) {
                        Log.d("좋아요 누르기 실패", "실패...")
                    }
                })
            }
        }

        viewBinding.ivZipdabangRecipeScrapUnfilled.setOnClickListener {

            viewBinding.ivZipdabangRecipeScrapUnfilled.visibility = View.GONE
            viewBinding.ivZipdabangRecipeScrapFilled.visibility = View.VISIBLE

            var newScrapNum = viewBinding.tvZipdabangRecipeScrapNum.text.toString().toInt() + 1
            viewBinding.tvZipdabangRecipeScrapNum.text = newScrapNum.toString()
            showScrapToast()

            // 이거 고치기
            GlobalScope.launch(Dispatchers.IO) {
                val token: Token = tokenDb.tokenDao().getToken()
                recipeService.pressScrap(token.token, idInt).enqueue(object: Callback<PressScrapResponse> {
                    override fun onResponse(
                        call: Call<PressScrapResponse>,
                        response: Response<PressScrapResponse>
                    ) {
                        Log.d("스크랩 누르기 성공", "${response.body()}")
                    }

                    override fun onFailure(call: Call<PressScrapResponse>, t: Throwable) {
                        Log.d("스크랩 누르기 실패", "${t.message}")
                    }
                })
            }

        }




        GlobalScope.launch(Dispatchers.IO) {
            val token: Token = tokenDb.tokenDao().getToken()
            recipeService.getDetailRecipe(token.token, idInt).enqueue(object : Callback<RecipeDetailResponse> {
                override fun onResponse(
                    call: Call<RecipeDetailResponse>,
                    response: Response<RecipeDetailResponse>
                ) {
                    val recipeDetailResult = response.body()
                    Log.d("상세 레시피 get 성공", "${recipeDetailResult}")


                    val ingredientsList = arrayListOf<IngredientDetail>()
                    val stepsList = arrayListOf<Step>()
                    val recipeInfo = recipeDetailResult?.recipeDataClass?.recipe?.get(0)
                    // 이거 2개 배열로 오니까 파싱해야함.
                    val ingredients = recipeDetailResult?.recipeDataClass?.ingredient
                    val steps = recipeDetailResult?.recipeDataClass?.steps
                    for (i in 0 until ingredients!!.size) {
                        ingredientsList.add(IngredientDetail(ingredients?.get(i)?.name.toString(), ingredients?.get(i)?.quantity.toString()))
                    }


                    val ingredientsRVAdapter = IngredientsRVAdapter(ingredientsList)
                    viewBinding.rvZipdabangRecipeDetailIngredients.layoutManager = LinearLayoutManager(this@ZipdabangRecipeDetailActivity)
                    viewBinding.rvZipdabangRecipeDetailIngredients.adapter = ingredientsRVAdapter

                    // 스텝

                    for (i in 0 until steps!!.size) {
                        stepsList.add(Step(steps?.get(i)?.step, steps?.get(i)?.stepDescription.toString(), steps?.get(i)?.stepImageUrl.toString()))
                    }
                    val recipeOrderRVAdapter = RecipeOrderRVAdapter(stepsList)
                    viewBinding.rvZipdabangRecipeDetailOrder.layoutManager = LinearLayoutManager(this@ZipdabangRecipeDetailActivity)
                    viewBinding.rvZipdabangRecipeDetailOrder.adapter = recipeOrderRVAdapter


                    val likes = recipeDetailResult?.recipeDataClass?.recipe?.get(0)?.likes
                    val scraps = recipeDetailResult?.recipeDataClass?.scraps
                    val comments = recipeDetailResult?.recipeDataClass?.comments
                    val likeOrNot = recipeDetailResult?.recipeDataClass?.liked
                    val scrapped = recipeDetailResult?.recipeDataClass?.scraped
                    val challengersNum = recipeDetailResult?.recipeDataClass?.challenger
                    val recipeImageUrl = recipeDetailResult?.recipeDataClass?.recipe?.get(0)?.imageUrl
                    val challengeStatus = recipeDetailResult?.recipeDataClass?.isChallenge

                    mainImageUrl = recipeImageUrl.toString()

//                    mainImageUrl = recipeImageUrl

                    if (likeOrNot == true) {
                        like = true
                        viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_filled)
                    } else {
                        like = false
                        viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)
                    }

                    scrap = scrapped

                    if (scrapped == true) {
                        scrap = true
                        viewBinding.ivZipdabangRecipeScrapFilled.visibility = View.VISIBLE
                        viewBinding.ivZipdabangRecipeScrapUnfilled.visibility = View.GONE
                    } else {
                        scrap = false
                        viewBinding.ivZipdabangRecipeScrapFilled.visibility = View.GONE
                        viewBinding.ivZipdabangRecipeScrapUnfilled.visibility = View.VISIBLE
                    }

                    if (challengeStatus == 0) {
                        viewBinding.btnChallengeStart.visibility = View.VISIBLE
                        viewBinding.btnChallengeComplete.visibility = View.INVISIBLE
                        viewBinding.btnChallengeRestart.visibility = View.INVISIBLE
                    } else if (challengeStatus == 1) {
                        viewBinding.btnChallengeStart.visibility = View.INVISIBLE
                        viewBinding.btnChallengeComplete.visibility = View.VISIBLE
                        viewBinding.btnChallengeRestart.visibility = View.INVISIBLE
                    } else {
                        viewBinding.btnChallengeStart.visibility = View.INVISIBLE
                        viewBinding.btnChallengeComplete.visibility = View.INVISIBLE
                        viewBinding.btnChallengeRestart.visibility = View.VISIBLE
                    }

                    Glide.with(this@ZipdabangRecipeDetailActivity)
                        .load(recipeImageUrl)
                        .into(viewBinding.viewpagerZipdabangRecipeDetailBanner)

//                    val dialogImageView = findViewById<ImageView>(R.id.iv_popup_recipe_image)
//                    Glide.with(this@ZipdabangRecipeDetailActivity)
//                        .load(recipeImageUrl)
//                        .into(dialogImageView)

                    viewBinding.tvZipdabangRecipeDetailTitle.text = recipeInfo?.name
                    viewBinding.tvZipdabangRecipeDetailNickname.text = "집다방 레시피"
                    viewBinding.tvZipdabangRecipeDetailNum.text = recipeInfo?.takeTime.toString()
                    viewBinding.tvZipdabangRecipeDetailDescription.text = recipeInfo?.intro
                    viewBinding.tvZipdabangRecipeDetailTipContent.text = recipeInfo?.review
                    viewBinding.tvZipdabangRecipeCommentNum.text = comments.toString()
                    viewBinding.tvZipdabangRecipeScrapNum.text = scraps.toString()
                    viewBinding.tvZipdabangRecipeLikeNum.text = likes.toString()
                    viewBinding.tvZipdabangRecipeDetailChallengeNum.text = challengersNum.toString()


                }

                override fun onFailure(call: Call<RecipeDetailResponse>, t: Throwable) {
                    Log.d("상세 레시피 get 실패", "실패")
                }
            })
        }

        // 댓글 리사이클러 뷰 어댑터 연결
        val recipeDetailCommentList: ArrayList<Comment> = arrayListOf()
        val recipeDetailCommentRVAdapter = RecipeDetailCommentRVAdapter(recipeDetailCommentList)




        GlobalScope.launch(Dispatchers.IO) {
            val token: Token = tokenDb.tokenDao().getToken()
            recipeService.getThreeComments(token.token, idInt).enqueue(object: Callback<ThreeCommentsResponse> {
                override fun onResponse(
                    call: Call<ThreeCommentsResponse>,
                    response: Response<ThreeCommentsResponse>
                ) {
                    val threeCommentsResult = response.body()
                    Log.d("상세 레시피 댓글 3개 가져오기 성공", "${threeCommentsResult}")
                    val threeComments = threeCommentsResult?.data?.comments
                    val threeCommentsList = arrayListOf<ThreeComments?>()
                    for (i in 0 until threeComments!!.size) {
                        threeCommentsList.add(threeComments?.get(i))
                    }

                    val threeCommentsProfile = arrayListOf<String?>()
                    val threeCommentsDateTime = arrayListOf<String?>()
                    val threeCommentsNickname = arrayListOf<String?>()
                    val threeCommentsContent = arrayListOf<String?>()

                    for (i in 0 until threeComments.size) {
                        threeCommentsProfile.add(threeCommentsList[i]?.profile)
                        val dateTimeArray = threeCommentsList[i]?.createdAt?.split('T')
                        val date = dateTimeArray?.get(0)
                        val time = dateTimeArray?.get(1)

                        recipeDetailCommentList.apply {
                            add(Comment(
                                threeCommentsList[i]?.profile,
                                threeCommentsList[i]?.nickname,
                                date,
                                time,
                                threeCommentsList[i]?.body,
                                null,
                                null
                                )
                            )
                        }
                    }
                    viewBinding.rvZipdabangRecipeComments.layoutManager = LinearLayoutManager(this@ZipdabangRecipeDetailActivity)
                    viewBinding.rvZipdabangRecipeComments.adapter = recipeDetailCommentRVAdapter

                }


                override fun onFailure(call: Call<ThreeCommentsResponse>, t: Throwable) {
                    Log.d("상세 레시피 댓글 3개 가져오기 실패", "실패")
                }
            })
        }

        // 스텝 리사이클러 뷰 어댑터 연결






        viewBinding.tvZipdabangRecipeCommentViewDetail.setOnClickListener {
            val intent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)
            intent.putExtra("recipeId", idInt.toString())
            startActivity(intent)
        }

        viewBinding.btnZipdabangRecipeWriteComment.setOnClickListener {
            viewBinding.btnZipdabangRecipeWriteComment.visibility = View.INVISIBLE
            viewBinding.layoutWriteComment.visibility = View.VISIBLE
            viewBinding.editTextComment.visibility = View.VISIBLE
            viewBinding.ivUploadComment.visibility = View.VISIBLE
        }

        viewBinding.ivUploadComment.setOnClickListener {
            if (viewBinding.editTextComment.text != null) {
                val commentBody: String = viewBinding.editTextComment.text.toString()
                // 이거는 나중에 고쳐줘야함!!!
                val recipeId: Int? = idInt
                GlobalScope.launch(Dispatchers.IO) {

                    val token: Token = tokenDb.tokenDao().getToken()
                    val commentRetrofit = retrofit2.Retrofit.Builder()
                        .baseUrl("http://zipdabang.store:3000")
                        .addConverterFactory(GsonConverterFactory.create()).build()
                    val commentService = commentRetrofit.create(RecipeService::class.java)
                    val tempToken = token.token.toString()
                    withContext(Dispatchers.Main) {
                        commentService.addComment(tempToken, CommentAddBody(recipeId, commentBody)).enqueue(object: Callback<CommentAddResponse> {
                            override fun onResponse(
                                call: Call<CommentAddResponse>,
                                response: Response<CommentAddResponse>
                            ) {
                                val addCommentResult = response.body()
                                Log.d("레시피 댓글 추가 성공", "${addCommentResult}")
                                commentService.getThreeComments(tempToken, recipeId).enqueue(object: Callback<ThreeCommentsResponse> {
                                    override fun onResponse(
                                        call: Call<ThreeCommentsResponse>,
                                        response: Response<ThreeCommentsResponse>
                                    ) {
                                        var threeCommentsLoadResult = response.body()
                                        Log.d("상세 레시피 댓글 3개 가져오기 성공", "${threeCommentsLoadResult}")
                                        var threeLoadComments = threeCommentsLoadResult?.data?.comments
                                        val threeLoadCommentsList = arrayListOf<ThreeComments?>()
                                        for (i in 0 until threeLoadComments!!.size) {
                                            threeLoadCommentsList.add(threeLoadComments?.get(i))
                                        }

                                        val threeCommentsProfile = arrayListOf<String?>()
                                        val threeCommentsDateTime = arrayListOf<String?>()
                                        val threeCommentsNickname = arrayListOf<String?>()
                                        val threeCommentsContent = arrayListOf<String?>()

                                        var recipeDetailCommentLoadList: ArrayList<Comment> = arrayListOf()

                                        for (i in 0 until threeLoadComments.size) {
                                            threeCommentsProfile.add(threeLoadCommentsList[i]?.profile)
                                            val dateTimeArray = threeLoadCommentsList[i]?.createdAt?.split('T')
                                            val date = dateTimeArray?.get(0)
                                            val time = dateTimeArray?.get(1)




                                            recipeDetailCommentLoadList.apply {
                                                add(Comment(
                                                    threeLoadCommentsList[i]?.profile,
                                                    threeLoadCommentsList[i]?.nickname,
                                                    date,
                                                    time,
                                                    threeLoadCommentsList[i]?.body,
                                                    null,
                                                    null
                                                )
                                                )
                                            }
                                        }
                                        val recipeDetailCommentLoadRVAdapter = RecipeDetailCommentRVAdapter(recipeDetailCommentLoadList)
                                        viewBinding.rvZipdabangRecipeComments.layoutManager = LinearLayoutManager(this@ZipdabangRecipeDetailActivity)
                                        viewBinding.rvZipdabangRecipeComments.adapter = recipeDetailCommentLoadRVAdapter
                                    }

                                    override fun onFailure(
                                        call: Call<ThreeCommentsResponse>,
                                        t: Throwable
                                    ) {
                                        Log.d("댓글 반영 실패", "${t.message}")
                                    }

                                })

                            }

                            override fun onFailure(call: Call<CommentAddResponse>, t: Throwable) {
                                Log.d("레시피 댓글 추가", "실패")
                            }
                        })
                    }

                }
                viewBinding.layoutWriteComment.visibility = View.INVISIBLE
                viewBinding.editTextComment.visibility = View.INVISIBLE
                viewBinding.ivUploadComment.visibility = View.INVISIBLE
                viewBinding.editTextComment.text = null
                viewBinding.btnZipdabangRecipeWriteComment.visibility = View.VISIBLE

                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.BOTTOM, 0, 0)
                    val layout: View = layoutInflater.inflate(
                        R.layout.toast_comment_upload_layout, findViewById(
                            R.id.toast_comment_upload
                        )
                    )
                    view = layout
                }.show()
            }
        }

        //=========================레시피 도전 화면 start=======================

        // 하단의 도전버튼 눌렀을 때의 동작들
        viewBinding.btnChallengeStart.setOnClickListener {
            // 버튼 교체
            viewBinding.btnChallengeStart.visibility = View.INVISIBLE
            viewBinding.btnChallengeComplete.visibility = View.VISIBLE

            // 설명글 교체
            viewBinding.layoutZipdabangRecipeDetailChallengers.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailCurrent.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailChallengeNum.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailChallenge.visibility = View.INVISIBLE

            viewBinding.tvZipdabangRecipeDetailChallenging.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                val recipeId = idInt
                val tokenDb = TokenDatabase.getTokenDatabase(this@ZipdabangRecipeDetailActivity)
                val token: Token = tokenDb.tokenDao().getToken()
                recipeService.pressChallenge(token.token, recipeId).enqueue(object : Callback<ChallengeResponse> {
                    override fun onResponse(
                        call: Call<ChallengeResponse>,
                        response: Response<ChallengeResponse>
                    ) {
                        Log.d("챌린지 버튼 누르기 성공", "${response.body()}")
                    }

                    override fun onFailure(call: Call<ChallengeResponse>, t: Throwable) {
                        Log.d("챌린지 버튼 누르기 실패", "실패")
                    }
                })
            }
        }

        viewBinding.btnChallengeComplete.setOnClickListener {
            viewBinding.btnChallengeComplete.visibility = View.INVISIBLE
            viewBinding.btnChallengeRestart.visibility = View.VISIBLE

            viewBinding.tvZipdabangRecipeDetailChallenging.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailSucceeded.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                val recipeId = idInt
                val tokenDb = TokenDatabase.getTokenDatabase(this@ZipdabangRecipeDetailActivity)
                val token: Token = tokenDb.tokenDao().getToken()
                recipeService.pressChallenge(token.token, recipeId).enqueue(object : Callback<ChallengeResponse> {
                    override fun onResponse(
                        call: Call<ChallengeResponse>,
                        response: Response<ChallengeResponse>
                    ) {
                        Log.d("챌린지 버튼 누르기 성공", "${response.body()}")
                    }

                    override fun onFailure(call: Call<ChallengeResponse>, t: Throwable) {
                        Log.d("챌린지 버튼 누르기 실패", "실패")
                    }
                })
            }

            dialogBinding = RecipeSuccessDialogBinding.inflate(layoutInflater)
            val successDialogView = LayoutInflater.from(this).inflate(R.layout.recipe_success_dialog, null)
            val successDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogBinding.root)

            val successDialog = successDialogBuilder.create()
            successDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            successDialog.window?.setGravity(Gravity.BOTTOM)
            successDialog.window?.attributes?.width = WindowManager.LayoutParams.WRAP_CONTENT
            successDialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT


            successDialog.show()

            val exitButton = successDialogView.findViewById<ImageView>(R.id.iv_exit_popup)
            val commentButton = successDialogView.findViewById<TextView>(R.id.btn_popup_comment)
            val laterButton = successDialogView.findViewById<TextView>(R.id.tv_popup_comment_later)
//            Glide.with(this).load(mainImageUrl)
//                .into(findViewById(R.id.iv_popup_recipe_image))

            Glide.with(this).load(mainImageUrl)
                .into(dialogBinding.ivPopupRecipeImage)
            dialogBinding.ivExitPopup.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()
            }

            dialogBinding.btnPopupComment.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()

                // 액티비티마다 아래 도착 액티비티 수정 필요!!
                val commentIntent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)
                commentIntent.putExtra("recipeId", idInt.toString())
                startActivity(commentIntent)
            }

            dialogBinding.tvPopupCommentLater.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()
            }
        }
        //=========================레시피 도전 화면 end=======================
    }

    private fun showLikeToast() {

        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_like_layout, findViewById(R.id.toast_like))
            view = layout
        }.show()
    }

    private fun showLikeCancelToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_like_cancel_layout, findViewById(R.id.toast_like_cancel))
            view = layout
        }.show()
    }

    private fun showScrapToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_scrap_layout, findViewById(R.id.toast_scrap))
            view = layout
        }.show()
    }

    private fun showScrapCancelToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_scrap_cancel_layout, findViewById(R.id.toast_scrap_cancel))
            view = layout
        }.show()
    }
}