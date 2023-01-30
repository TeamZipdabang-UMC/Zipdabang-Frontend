package com.example.umc_zipdabang.config.src.main.Our

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
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailCommentActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.Ingredient
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.RecipeOrder
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.IngredientsRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.RecipeDetailCommentRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.RecipeOrderRVAdapter
import com.example.umc_zipdabang.databinding.ActivityOurRecipeDetailBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.databinding.RecipeSuccessDialogBinding
import com.google.android.gms.auth.TokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class OurRecipeDetailActivity: AppCompatActivity() {
    private lateinit var dialogBinding: RecipeSuccessDialogBinding
    private lateinit var viewBinding: ActivityOurRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2
    // 사용자에 따라 달라짐
    private var like: Boolean? = false
    private var scrap: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityOurRecipeDetailBinding.inflate(layoutInflater)
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

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
            finish()
        }

        viewBinding.ivOurRecipeLike.setOnClickListener {
            if (!like!!) {
                like = true
                viewBinding.ivOurRecipeLike.setImageResource(R.drawable.ic_heart_filled)

                var newLikeNum = viewBinding.tvOurRecipeLikeNum.text.toString().toInt() + 1
                viewBinding.tvOurRecipeLikeNum.setText(newLikeNum.toString())
                showLikeToast()
            } else {
                like = false
                viewBinding.ivOurRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)

                var newLikeNum = viewBinding.tvOurRecipeLikeNum.text.toString().toInt() - 1
                viewBinding.tvOurRecipeLikeNum.setText(newLikeNum.toString())
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

        viewBinding.ivOurRecipeScrapUnfilled.setOnClickListener {
            viewBinding.ivOurRecipeScrapUnfilled.visibility = View.GONE
            viewBinding.ivOurRecipeScrapFilled.visibility = View.VISIBLE

            var newScrapNum = viewBinding.tvOurRecipeScrapNum.text.toString().toInt() + 1
            viewBinding.tvOurRecipeScrapNum.text = newScrapNum.toString()
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
            // 이거 고치기

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
                        Log.d("얻어온 재료", "${ingredients?.get(i)?.name.toString()}")
                        Log.d("얻어온 재료", "${ingredients?.get(i)?.quantity.toString()}")
                    }
                    Log.d("재료 리스트", "${ingredientsList}")

                    val ingredientsRVAdapter = IngredientsRVAdapter(ingredientsList)
                    viewBinding.rvOurRecipeDetailIngredients.layoutManager = LinearLayoutManager(this@OurRecipeDetailActivity)
                    viewBinding.rvOurRecipeDetailIngredients.adapter = ingredientsRVAdapter

                    // 스텝

                    for (i in 0 until steps!!.size) {
                        stepsList.add(Step(steps?.get(i)?.step, steps?.get(i)?.stepDescription.toString(), steps?.get(i)?.stepImageUrl.toString()))
                    }
                    val recipeOrderRVAdapter = RecipeOrderRVAdapter(stepsList)
                    viewBinding.rvOurRecipeDetailOrder.layoutManager = LinearLayoutManager(this@OurRecipeDetailActivity)
                    viewBinding.rvOurRecipeDetailOrder.adapter = recipeOrderRVAdapter

                    val likes = recipeDetailResult?.recipeDataClass?.recipe?.get(0)?.likes
                    val scraps = recipeDetailResult?.recipeDataClass?.scraps
                    val comments = recipeDetailResult?.recipeDataClass?.comments
                    val likeOrNot = recipeDetailResult?.recipeDataClass?.liked
                    val scrapped = recipeDetailResult?.recipeDataClass?.scraped
                    val challengersNum = recipeDetailResult?.recipeDataClass?.challenger
                    val recipeImageUrl = recipeDetailResult?.recipeDataClass?.recipe?.get(0)?.imageUrl
                    val challengeStatus = recipeDetailResult?.recipeDataClass?.isChallenge

                    mainImageUrl = recipeImageUrl.toString()
                    Log.d("레시피 메인 이미지 url", "${mainImageUrl}")


                    if (likeOrNot == true) {
                        like = true
                        viewBinding.ivOurRecipeLike.setImageResource(R.drawable.ic_heart_filled)
                    } else {
                        like = false
                        viewBinding.ivOurRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)
                    }

                    scrap = scrapped

                    if (scrapped == true) {
                        scrap = true
                        viewBinding.ivOurRecipeScrapFilled.visibility = View.VISIBLE
                        viewBinding.ivOurRecipeScrapUnfilled.visibility = View.INVISIBLE
                    } else {
                        scrap = false
                        viewBinding.ivOurRecipeScrapFilled.visibility = View.INVISIBLE
                        viewBinding.ivOurRecipeScrapUnfilled.visibility = View.VISIBLE
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

                    Glide.with(this@OurRecipeDetailActivity)
                        .load(recipeImageUrl)
                        .into(viewBinding.viewpagerOurRecipeDetailBanner)

//                    val dialogImageView = findViewById<ImageView>(R.id.iv_popup_recipe_image)
//                    Glide.with(this@ZipdabangRecipeDetailActivity)
//                        .load(recipeImageUrl)
//                        .into(dialogImageView)

                    viewBinding.tvOurRecipeDetailTitle.text = recipeInfo?.name
                    viewBinding.tvOurRecipeDetailNickname.text = "우리들의 레시피"
                    viewBinding.tvOurRecipeDetailNum.text = recipeInfo?.takeTime.toString()
                    viewBinding.tvOurRecipeDetailDescription.text = recipeInfo?.intro
                    viewBinding.tvOurRecipeDetailTipContent.text = recipeInfo?.review
                    viewBinding.tvOurRecipeCommentNum.text = comments.toString()
                    viewBinding.tvOurRecipeScrapNum.text = scraps.toString()
                    viewBinding.tvOurRecipeLikeNum.text = likes.toString()
                    viewBinding.tvOurRecipeDetailChallengeNum.text = challengersNum.toString()
//                    viewBinding.tvOurRecipeDetailNickname.text =


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
                    viewBinding.rvOurRecipeComments.layoutManager = LinearLayoutManager(this@OurRecipeDetailActivity)
                    viewBinding.rvOurRecipeComments.adapter = recipeDetailCommentRVAdapter

                }


                override fun onFailure(call: Call<ThreeCommentsResponse>, t: Throwable) {
                    Log.d("상세 레시피 댓글 3개 가져오기 실패", "실패")
                }
            })
        }

        // 스텝 리사이클러 뷰 어댑터 연결






        viewBinding.tvOurRecipeCommentViewDetail.setOnClickListener {
            val intent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)
            intent.putExtra("recipeId", idInt.toString())
            startActivity(intent)
        }

        viewBinding.btnOurRecipeWriteComment.setOnClickListener {
            viewBinding.btnOurRecipeWriteComment.visibility = View.INVISIBLE
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
                viewBinding.btnOurRecipeWriteComment.visibility = View.VISIBLE

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
            viewBinding.layoutOurRecipeDetailChallengers.visibility = View.INVISIBLE
            viewBinding.tvOurRecipeDetailCurrent.visibility = View.INVISIBLE
            viewBinding.tvOurRecipeDetailChallengeNum.visibility = View.INVISIBLE
            viewBinding.tvOurRecipeDetailChallenge.visibility = View.INVISIBLE

            viewBinding.tvOurRecipeDetailChallenging.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                val recipeId = idInt
                val tokenDb = TokenDatabase.getTokenDatabase(this@OurRecipeDetailActivity)
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

            viewBinding.tvOurRecipeDetailChallenging.visibility = View.INVISIBLE
            viewBinding.tvOurRecipeDetailSucceeded.visibility = View.VISIBLE

            GlobalScope.launch(Dispatchers.IO) {
                val recipeId = idInt
                val tokenDb = TokenDatabase.getTokenDatabase(this@OurRecipeDetailActivity)
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
            val successDialogBuilder = AlertDialog.Builder(this).setView(dialogBinding.root)
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

            Log.d("팝업 레시피 메인 이미지 url", "${mainImageUrl}")
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