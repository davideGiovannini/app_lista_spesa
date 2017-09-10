package com.erindavide.listaspesa.model

import org.jetbrains.anko.db.RowParser


class Product(var name: String, var id: Long = 0, var price: Double = 0.0, var quantity: Int = 1){

    companion object {
        val parser = object : RowParser<Product> {
            override fun parseRow(columns: Array<Any?>): Product {
                return Product(columns[1] as String, columns[0] as Long, columns[2] as Double, (columns[3] as Long).toInt())
            }
        }
    }
}