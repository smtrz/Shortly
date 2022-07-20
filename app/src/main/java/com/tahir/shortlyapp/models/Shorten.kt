package com.tahir.shortlyapp.models

import com.google.gson.annotations.SerializedName


data class Shorten (

  @SerializedName("ok"     ) var ok     : Boolean? = null,
  @SerializedName("result" ) var result : Result?  = Result()

)