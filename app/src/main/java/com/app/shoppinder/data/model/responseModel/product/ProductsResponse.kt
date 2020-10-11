package com.app.shoppinder.data.model.responseModel.product

import com.google.gson.annotations.SerializedName

data class ProductsResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class CategoriesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Pagination(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

data class Meta(

	@field:SerializedName("pagination")
	val pagination: Pagination? = null
)

data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("discount_amount")
	val discountAmount: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	var quantity: Int? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
