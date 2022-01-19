package com.sandymist.helloretrofit.model

import com.sandymist.helloretrofit.model.Fruit

data class FruitResponse (
    val status: String,
    val data: List<Fruit>
)