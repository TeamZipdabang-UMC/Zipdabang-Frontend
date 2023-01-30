package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.CommentDbClass
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.Token
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit
import com.example.umc_zipdabang.config.src.main.SocialLogin.InitialActivity
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBinding
import com.example.umc_zipdabang.databinding.ItemCommentBinding
import kotlinx.android.synthetic.main.activity_zipdabang_recipe_detail.*
import kotlinx.android.synthetic.main.item_loading.*
import kotlinx.coroutines.*
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class ZipdabangRecipeDetailCommentActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBinding
    private val commentNumberList =
        ArrayList<com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment>()
    private var page = 1
    private var isLoading = false
    private var limit = 12


    private lateinit var progressBar: ProgressBar

    lateinit var adapter: CommentInfiniteRVAdapter
    lateinit var layoutManager: LinearLayoutManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailCommentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val selectedRecipeId = intent.getStringExtra("recipeId")
        Log.d("댓글 레시피 아이디 가져오기 성공", "${selectedRecipeId}")
        val idInt = selectedRecipeId?.toInt()

        progressBar = viewBinding.progressBar

        layoutManager = LinearLayoutManager(this)
        viewBinding.rvZipdabangRecipeDetailComment.layoutManager = layoutManager

        val commentRetrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val commentService = commentRetrofit.create(RecipeService::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val goToLogin = Intent(this, InitialActivity::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val token: Token = tokenDb.tokenDao().getToken()
            if (token.token == "") {
                startActivity(goToLogin)
            }
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")
            commentService.getRecipeComments(tokenNum, idInt)
                .enqueue(object : Callback<CommentsResponse> {
                    override fun onResponse(
                        call: Call<CommentsResponse>,
                        response: Response<CommentsResponse>
                    ) {
                        val result = response.body()
                        Log.d("레시피 댓글 Get 성공", "${result}")
                        var firstResultArray = arrayListOf<AllComments?>()

                        for (i in 0 until result?.data!!.comments.size) {
                            val firstResult = result?.data?.comments?.get(i)
                            firstResultArray.add(firstResult)
                            Log.d("첫번째 배열 요소", "${firstResultArray}")
                        }



                        val commentIdArray = arrayListOf<Int?>()
                        val commentOwnerArray = arrayListOf<Int?>()
                        val commentBodyArray = arrayListOf<String?>()
                        val commentDateTimeArray = arrayListOf<String?>()
                        val commentNicknameArray = arrayListOf<String?>()
                        val commentProfileUrlArray = arrayListOf<String?>()

                        for (i in 0 until firstResultArray.size) {

                            commentIdArray.add(firstResultArray[i]?.commentId)
                            commentOwnerArray.add(firstResultArray[i]?.owner)
                            commentBodyArray.add(firstResultArray[i]?.body)
                            commentDateTimeArray.add(firstResultArray[i]?.createdAt)
                            commentNicknameArray.add(firstResultArray[i]?.nickname)
                            commentProfileUrlArray.add(firstResultArray[i]?.profile)




                            val dateTimeArray = firstResultArray[i]?.createdAt?.split('T')
                            val date = dateTimeArray?.get(0)
                            val time = dateTimeArray?.get(1)
                            commentNumberList.add(
                                Comment(
                                    firstResultArray[i]?.profile,
                                    firstResultArray[i]?.nickname,
                                    date,
                                    time,
                                    firstResultArray[i]?.body,
                                    firstResultArray[i]?.commentId,
                                    firstResultArray[i]?.owner
                                )
                            )
                        }
                        Handler().postDelayed({
                            if (::adapter.isInitialized) {
                                adapter.notifyDataSetChanged()
                            } else {
                                adapter =
                                    ZipdabangRecipeDetailCommentActivity.CommentInfiniteRVAdapter(
                                        this@ZipdabangRecipeDetailCommentActivity,
                                        commentNumberList
                                    )
                                // 여기 고치기
                                viewBinding.rvZipdabangRecipeDetailComment.layoutManager =
                                    layoutManager
                                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter

                            }
                            isLoading = false
                            progressBar.visibility = View.GONE
                        }, 2000)

                        isLoading = true
                        progressBar.visibility = View.VISIBLE

                        val start = (page - 1) * limit
                        val end = (page) * limit

//                    for (i in start..end) {
//                        // 여기에 해당 들어갈 내용을 넣기
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                        commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
//                    }

                        viewBinding.rvZipdabangRecipeDetailComment.addOnScrollListener(object :
                            RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                if (dy > 0) {
                                    val visibleItemCount = layoutManager.childCount
                                    val pastVisibleItem =
                                        layoutManager.findFirstCompletelyVisibleItemPosition()
                                    val total = adapter.itemCount

                                    if (!isLoading) {
                                        if ((visibleItemCount + pastVisibleItem) >= total) {
                                            page += 1
//                                        getPage()
                                            isLoading = true
                                            progressBar.visibility = View.VISIBLE

                                            val start = (page - 1) * limit
                                            val end = (page) * limit

                                            GlobalScope.launch {
                                                delay(1000)
                                                withContext(Dispatchers.Main) {
                                                    commentService.getMoreRecipeComments(
                                                        tokenNum,
                                                        idInt,
                                                        commentIdArray.get(commentIdArray.size - 1)
                                                    ).enqueue(object : Callback<CommentsResponse> {
                                                        override fun onResponse(
                                                            call: Call<CommentsResponse>,
                                                            response: Response<CommentsResponse>
                                                        ) {
                                                            var moreResult = response.body()
                                                            firstResultArray =
                                                                ArrayList<AllComments?>()
                                                            Log.d(
                                                                "more result 댓글 결과",
                                                                "${moreResult}"
                                                            )
                                                            for (i in 0 until moreResult?.data!!.comments.size) {
                                                                val newResult = moreResult?.data?.comments?.get(i)
                                                                firstResultArray.add(newResult)
                                                                Log.d("첫번째 배열 요소", "${firstResultArray}")
                                                            }
                                                            Log.d(
                                                                "댓글 last",
                                                                "${commentIdArray.get(commentIdArray.size - 1)}"
                                                            )

                                                            for (i in 0 until moreResult?.data?.comments!!.size) {
                                                                commentIdArray.add(firstResultArray[i]?.commentId)
                                                                commentOwnerArray.add(
                                                                    firstResultArray[i]?.owner
                                                                )
                                                                commentBodyArray.add(
                                                                    firstResultArray[i]?.body
                                                                )
                                                                commentDateTimeArray.add(
                                                                    firstResultArray[i]?.createdAt
                                                                )
                                                                commentNicknameArray.add(
                                                                    firstResultArray[i]?.nickname
                                                                )
                                                                commentProfileUrlArray.add(
                                                                    firstResultArray[i]?.profile
                                                                )

                                                                Log.d(
                                                                    "${i}번째 아이디",
                                                                    "${firstResultArray[i]?.commentId}"
                                                                )
                                                                Log.d(
                                                                    "${i}번째 오너",
                                                                    "${firstResultArray[i]?.owner}"
                                                                )
                                                                Log.d(
                                                                    "${i}번째 바디",
                                                                    "${firstResultArray[i]?.body}"
                                                                )
                                                                Log.d(
                                                                    "${i}번째 시간",
                                                                    "${firstResultArray[i]?.createdAt}"
                                                                )
                                                                Log.d(
                                                                    "${i}번째 닉네임",
                                                                    "${firstResultArray[i]?.nickname}"
                                                                )
                                                                Log.d(
                                                                    "${i}번째 프사",
                                                                    "${firstResultArray[i]?.profile}"
                                                                )
                                                                val dateTimeNewArray =
                                                                    firstResultArray[i]?.createdAt?.split(
                                                                        'T'
                                                                    )
                                                                val newDate =
                                                                    dateTimeNewArray?.get(0)
                                                                val newTime =
                                                                    dateTimeNewArray?.get(1)
                                                                commentNumberList.add(
                                                                    Comment(
                                                                        firstResultArray[i]?.profile,
                                                                        firstResultArray[i]?.nickname,
                                                                        newDate,
                                                                        newTime,
                                                                        firstResultArray[i]?.body,
                                                                        firstResultArray[i]?.commentId,
                                                                        firstResultArray[i]?.owner
                                                                    )
                                                                )
                                                            }
                                                        }

                                                        override fun onFailure(
                                                            call: Call<CommentsResponse>,
                                                            t: Throwable
                                                        ) {
                                                            Log.d("추가댓글 불러오기", "실패")
                                                        }
                                                    })
                                                }
                                            }


                                            Handler().postDelayed({
                                                if (::adapter.isInitialized) {
                                                    adapter.notifyDataSetChanged()
                                                } else {
                                                    adapter =
                                                        ZipdabangRecipeDetailCommentActivity.CommentInfiniteRVAdapter(
                                                            this@ZipdabangRecipeDetailCommentActivity,
                                                            commentNumberList
                                                        )
                                                    viewBinding.rvZipdabangRecipeDetailComment.adapter =
                                                        adapter
                                                }
                                                isLoading = false
                                                progressBar.visibility = View.GONE
                                            }, 2000)
                                        }
                                    }
                                }
                            }
                        })
                    }

                    override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                        Log.d("댓글 불러오기", "실패")
                    }
                })
        }

//        getPage()


        viewBinding.ivZipdabangRecipeDetailCommentsBackarrow.setOnClickListener {
            finish()
        }

        viewBinding.rvZipdabangRecipeDetailComment.addItemDecoration(AdapterDecoration())

        // 댓글 업로드 버튼 눌렀을 때
        viewBinding.ivUploadComment.setOnClickListener {
            if (viewBinding.editTextComment.text != null) {
                val commentBody: String = viewBinding.editTextComment.text.toString()
                // 이거는 나중에 고쳐줘야함!!!
//                val recipeId: Int = 49
                GlobalScope.launch(Dispatchers.IO) {

                    val tempToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
                    withContext(Dispatchers.Main) {
                        commentService.addComment(tempToken, CommentAddBody(idInt, commentBody)).enqueue(object: Callback<CommentAddResponse> {
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
                viewBinding.editTextComment.text = null
            }

        }

//        val deleteCommentView = findViewById<TextView>(R.id.btn_comment_final_delete)
//        deleteCommentView.setOnClickListener {
//            showCommentDeleteToast()
//        }


    }

    class CommentInfiniteRVAdapter(
        val activity: ZipdabangRecipeDetailCommentActivity,
        public val commentNumberList: ArrayList<com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment>
    ) : RecyclerView.Adapter<CommentInfiniteRVAdapter.CommentInfiniteViewHolder>() {

        class CommentInfiniteViewHolder(private var binding: ItemCommentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(
                context: Context,
                item: com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment,
                commentNumList: ArrayList<Comment>,
                viewbinding: ActivityZipdabangRecipeDetailCommentBinding
            ) {


                binding.tvCommentNickname.text = item.nickname
                binding.tvCommentDate.text = item.date
                Glide.with(context).load(item.profileImageUrl).into(binding.ivCommentProfile)
                binding.ivCommentProfile.clipToOutline = true
                binding.tvCommentTime.text = item.time
                binding.tvCommentContent.text = item.content

                binding.ivCommentControl.setOnClickListener {
                    val commentEditDeleteDialogView =
                        LayoutInflater.from(context).inflate(R.layout.comment_control_dialog, null)
                    val commentEditDeleteDialogBuilder =
                        AlertDialog.Builder(context).setView(commentEditDeleteDialogView)

                    val commentEditDeleteDialog = commentEditDeleteDialogBuilder.create()
                    commentEditDeleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    commentEditDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    commentEditDeleteDialog.window?.setGravity(Gravity.BOTTOM)
                    commentEditDeleteDialog.window?.attributes?.width =
                        WindowManager.LayoutParams.WRAP_CONTENT
                    commentEditDeleteDialog.window?.attributes?.height =
                        WindowManager.LayoutParams.WRAP_CONTENT

                    commentEditDeleteDialog.show()

                    val commentPopupExitButton =
                        commentEditDeleteDialogView.findViewById<ImageView>(R.id.btn_comment_control_exit)
                    val commentPopupEditButton =
                        commentEditDeleteDialogView.findViewById<TextView>(R.id.btn_comment_edit)
                    val commentPopupDeleteButton =
                        commentEditDeleteDialogView.findViewById<TextView>(R.id.btn_comment_delete)

                    commentPopupExitButton.setOnClickListener {
                        commentEditDeleteDialog.dismiss()
                    }

                    // 댓글 수정 선택 시
                    commentPopupEditButton.setOnClickListener {
                        val commentInfiniteRVAdapter = CommentInfiniteRVAdapter(ZipdabangRecipeDetailCommentActivity(), commentNumList)

                        val commentEditTokenDb = TokenDatabase.getTokenDatabase(context)

                        val commentEditRetrofit = retrofit2.Retrofit.Builder()
                            .baseUrl("http://zipdabang.store:3000")
                            .addConverterFactory(GsonConverterFactory.create()).build()
                        val commentEditService = commentEditRetrofit.create(RecipeService::class.java)
                        GlobalScope.launch(Dispatchers.IO) {

                            val commentEditLocation = adapterPosition
                            val selectedEditComment = commentNumList[adapterPosition]
                            val selectedEditommentId = selectedEditComment.commentId
                            val selectedEditCommentOwner = selectedEditComment.commentOwner
                            val editComment = viewbinding.editTextComment.text.toString()

//                            withContext(Dispatchers.Main) {
//                                val editToken = commentEditTokenDb.tokenDao().getToken().token
//                                // 시험용 토큰 넣음. 나중에 바꾸기. editToken으로 바꿀것.
//                                commentEditService.editComment("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0", CommentEditBody(selectedEditCommentOwner, selectedEditommentId, editComment)).enqueue(object : Callback<CommentEditResponse> {
//                                    override fun onResponse(
//                                        call: Call<CommentEditResponse>,
//                                        response: Response<CommentEditResponse>
//                                    ) {
//                                        if (response.code() == 200) {
//                                            Log.d("댓글 수정 가능", "${response.body()}")
//                                            // 리사이클러뷰에 알리기
//
//                                            commentInfiniteRVAdapter.notifyItemChanged(adapterPosition)
//                                        }
//                                        else if (response.code() == 400) {
//                                            Log.d("댓글 수정 불가능", "다른 사용자")
//                                        }
//                                    }
//
//                                    override fun onFailure(
//                                        call: Call<CommentEditResponse>,
//                                        t: Throwable
//                                    ) {
//                                        Log.d("댓글 수정 실패", ":통신 실패")
//                                    }
//                                })
//                            }
                        }

                    }

                    // 댓글 삭제 선택 시
                    commentPopupDeleteButton.setOnClickListener {
                        commentEditDeleteDialog.dismiss()

                        val commentDeleteDialogView = LayoutInflater.from(context)
                            .inflate(R.layout.comment_delete_dialog, null)
                        val commentDeleteDialogBuilder = AlertDialog.Builder(context)
                            .setView(commentDeleteDialogView)
                        val commentDeleteDialog = commentDeleteDialogBuilder.create()
                        commentDeleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        commentDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        commentDeleteDialog.window?.setGravity(Gravity.BOTTOM)
                        commentDeleteDialog.window?.attributes?.width =
                            WindowManager.LayoutParams.WRAP_CONTENT
                        commentDeleteDialog.window?.attributes?.height =
                            WindowManager.LayoutParams.WRAP_CONTENT

                        commentDeleteDialog.show()

                        val commentFinalDeleteButton =
                            commentDeleteDialogView.findViewById<TextView>(R.id.btn_comment_final_delete)
                        val commentFinalCancelButton =
                            commentDeleteDialogView.findViewById<TextView>(R.id.btn_comment_final_cancel)

                        // 정말 삭제하시겠습니까? - 삭제 클릭 시
                        commentFinalDeleteButton.setOnClickListener {
                            val commentDeleteTokenDb = TokenDatabase.getTokenDatabase(context)

                            GlobalScope.launch(Dispatchers.IO) {
                                val commentDeleteRetrofit = retrofit2.Retrofit.Builder()
                                    .baseUrl("http://zipdabang.store:3000")
                                    .addConverterFactory(GsonConverterFactory.create()).build()
                                val commentDeleteService = commentDeleteRetrofit.create(RecipeService::class.java)
                                val deleteToken = commentDeleteTokenDb.tokenDao().getToken().token



                                withContext(Dispatchers.Main) {
                                    val commentLocation = adapterPosition
                                    val selectedDeleteComment = commentNumList[adapterPosition]
                                    val selectedDeleteCommentId = selectedDeleteComment.commentId
                                    val selectedDeleteCommentOwner = selectedDeleteComment.commentOwner
                                    val tempToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"

                                    commentDeleteService.deleteComment(tempToken, CommentDeleteBody(selectedDeleteCommentOwner, selectedDeleteCommentId)).enqueue(object : Callback<CommentEditResponse> {
                                        override fun onResponse(
                                            call: Call<CommentEditResponse>,
                                            response: Response<CommentEditResponse>
                                        ) {
                                            val deleteResult = response.body()
                                            if (response.code() == 200) {
                                                val commentInfiniteRVAdapter = CommentInfiniteRVAdapter(ZipdabangRecipeDetailCommentActivity(), commentNumList)
                                                Log.d("레시피 댓글 Delete 성공", "${deleteResult}")

                                                commentNumList.removeAt(adapterPosition)
                                                // 삭제되었어요 토스트 필요

                                                commentDeleteDialog.dismiss()

                                                // 반영하기
                                                commentInfiniteRVAdapter.notifyItemRemoved(adapterPosition)
                                            } else if (response.code() == 401) {
                                                Log.d("댓글 토큰 없음", "없음")

                                            } else if (response.code() == 403) {
                                                Log.d("댓글 주인 아님", "삭제 불가능")
                                            }

                                        }

                                        override fun onFailure(
                                            call: Call<CommentEditResponse>,
                                            t: Throwable
                                        ) {
                                            Log.d("삭제 실패", "다시해라")
                                        }
                                    })
                                }

                                    // 데이터베이스로부터 해당 댓글



                            }

                        }

                        // 취소 클릭 시
                        commentFinalCancelButton.setOnClickListener {
                            commentDeleteDialog.dismiss()
                        }

                    }
                }

            }

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommentInfiniteRVAdapter.CommentInfiniteViewHolder {
            val viewBinding_viewholder =
                ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return CommentInfiniteRVAdapter.CommentInfiniteViewHolder(viewBinding_viewholder)


        }

        override fun onBindViewHolder(
            holder: CommentInfiniteRVAdapter.CommentInfiniteViewHolder,
            position: Int
        ) {
            holder.bind(activity, activity.commentNumberList[position], commentNumberList, ActivityZipdabangRecipeDetailCommentBinding.inflate(activity.layoutInflater))

        }

        override fun getItemCount(): Int {
            return activity.commentNumberList.size
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }
    }

//    private fun getPage() {
//        isLoading = true
//        progressBar.visibility = View.VISIBLE
//
//        val start = (page - 1) * limit
//        val end = (page) * limit
//
//        for (i in start..end) {
//            // 여기에 해당 들어갈 내용을 넣기
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//            commentNumberList.add(
//                Comment(
//                    "https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png",
//                    "김기문",
//                    "1234",
//                    "5678",
//                    "내입 썩는다."
//                )
//            )
//        }
//        Handler().postDelayed({
//            if (::adapter.isInitialized) {
//                adapter.notifyDataSetChanged()
//            } else {
//                adapter = ZipdabangRecipeDetailCommentActivity.CommentInfiniteRVAdapter(
//                    this,
//                    commentNumberList
//                )
//                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter
//            }
//            isLoading = false
//            progressBar.visibility = View.GONE
//        }, 2000)
//    }
//
//    // 토스트 띄우기
//    private fun showCommentDeleteToast() {
//        Toast(this).apply {
//            duration = Toast.LENGTH_SHORT
//            setGravity(Gravity.BOTTOM, 0, 0)
//            val layout: View = layoutInflater.inflate(
//                R.layout.toast_comment_delete_layout,
//                findViewById(R.id.toast_delete)
//            )
//            view = layout
//        }.show()
//
//    }


}