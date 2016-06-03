package com.erindavide.listaspesa.model

import org.jetbrains.anko.db.RowParser

/**
 * Created by linnal on 6/2/16.
 */

class Product(var name: String, var id: Long = 0, var price: Double = 0.0, var quantity: Int = 1){

    companion object {
        val parser = object : RowParser<Product> {
            override fun parseRow(columns: Array<Any>): Product {
                return Product(columns[0] as String, columns[1] as Long, columns[2] as Double, columns[3] as Int)
            }
        }
    }
}