package com.example.umc_zipdabang.src.my

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

//DTO 클래스 선언
data class PostNewRecipeBody(
    @SerializedName("recipe") var recipe: PostNewRecipeList?,
    @SerializedName("ingredient") var ingredient: ArrayList<PostNewRecipeIngredient>,
    @SerializedName("steps") var steps:ArrayList<PostNewRecipeSteps>
)
data class PostNewRecipeList(
    @SerializedName("category") var category : Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("intro") var intro : String?,
    @SerializedName("review") var review: String?,
    @SerializedName("take_time") var take_time : String?,
    @SerializedName("image_url") var image_url: String?,
    @SerializedName("step_size") var step_size: Int?,
    @SerializedName("ingredient_size") var ingredient_size: Int?
)
data class PostNewRecipeIngredient(
    @SerializedName("name") var name : String?,
    @SerializedName("quantity") var quantity: String?
)
data class PostNewRecipeSteps(
    @SerializedName("step") var step : Int?,
    @SerializedName("detail") var detail: String?,
    @SerializedName("step_image_url") var step_image_url: String?
)
data class PostNewRecipeBodyResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : String,
    @SerializedName("error") var error : String
)

data class GetNewRecipeSaveImageResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : GetNewRecipeImages,
)
data class GetNewRecipeImages(
    @SerializedName("thumb") var thumb : String?,
    @SerializedName("steps") var steps : ArrayList<GetNewRecipeImagesStep>,
)
data class GetNewRecipeImagesStep(
    @SerializedName("stepNum") var stepNum : Int?,
    @SerializedName("image") var image : String?,
)

data class PostNewRecipeSaveImage(
    @SerializedName("thumbnail") var thumbnail : String,
    @SerializedName("steps") var steps : ArrayList<PostNewRecipeStepsImage>,
    @SerializedName("step_size") var step_size : Int
)
data class PostNewRecipeStepsImage(
    @SerializedName("step") var step : String,
    @SerializedName("detail") var detail : String,
    @SerializedName("step_image_url") var step_image_url : String,
)

data class PostNewRecipeImageBodyResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var image: PostNewRecipeImage,
    @SerializedName("error") var error : String
)
data class PostNewRecipeImage(
    @SerializedName("image") var image : Object
)


//도전중
data class GetChallengingRecipeResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : myChallenging ,
    @SerializedName("error") var error : String
)
data class myChallenging(
    @SerializedName("myChallenging") var myChallenging : ArrayList<GetChallengingRecipe>
)
data class GetChallengingRecipe(
    @SerializedName("recipeId") var recipeId : Int,
    @SerializedName("likes") var likes : Int,
    @SerializedName("image") var image : String,
    @SerializedName("name") var name : String
)

//도전완료
data class GetChallengedoneRecipeResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : myComplete ,
    @SerializedName("error") var error : String
)
data class myComplete(
    @SerializedName("myComplete") var myComplete : ArrayList<GetChallengedoneRecipe>
)
data class GetChallengedoneRecipe(
    @SerializedName("recipeId") var recipeId : Int,
    @SerializedName("likes") var likes : Int,
    @SerializedName("image") var image : String,
    @SerializedName("name") var name : String
)

//도전완료
data class GetMyRecipeResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : ArrayList<GetMyRecipe> ,
    @SerializedName("error") var error : String
)
data class GetMyRecipe(
    @SerializedName("recipeId") var recipeId : Int,
    @SerializedName("name") var name : String,
    @SerializedName("likes") var likes : Int,
    @SerializedName("image") var image : String,
)


data class Delete(
    @SerializedName("target")
    var target : Array<Int>?
)

data class Scrap_Delete_Response (
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("data")
    var data : Any?,
    @SerializedName("error")
    var error: Any?
)
