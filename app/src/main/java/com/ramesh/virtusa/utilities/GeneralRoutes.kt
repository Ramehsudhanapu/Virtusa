package com.ramesh.virtusa.utilities

sealed  class GeneralRoutes(val  route:String)  {
    object Category: GeneralRoutes("categoryScreen")

    fun createRoute(categoryName:String)="productDetailsScreen/$categoryName"
    object Details : GeneralRoutes("detailScreen/{productId}")
    {
        fun createDetailRoute(productId:Int)="detailScreen/$productId"

    }

}