package com.erindavide.listaspesa.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.erindavide.listaspesa.model.Product
import org.jetbrains.anko.db.*

/**
 * Created by linnal on 6/2/16.
 */


const  val ENTRIES_TABLE = "product"
const val ROW_ID = "_id"
const val ROW_NAME = "name"
const val ROW_PRICE = "price"
const val ROW_QUANTITY = "quantity"
private const val TAG = "DatabaseManager"


class DatabaseManager(context: Context) : ManagedSQLiteOpenHelper(context, "ShareShopDB", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(ENTRIES_TABLE, true, // IF_NOT_EXISTS
                ROW_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ROW_NAME to TEXT + NOT_NULL,
                ROW_PRICE to TEXT,
                ROW_QUANTITY to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(ENTRIES_TABLE)
        onCreate(db)
    }

    companion object {
        private var instance: DatabaseManager? = null

        fun getInstance(ctx: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(ctx.applicationContext)
            }
            return instance!!
        }
    }

}


val Context.database: DatabaseManager
    get() = DatabaseManager.getInstance(applicationContext)


fun SQLiteDatabase.insertProduct(product: Product) {

    insert(ENTRIES_TABLE,
            ROW_NAME to product.name,
            ROW_PRICE to product.price,
            ROW_QUANTITY to product.quantity
    )
}

fun SQLiteDatabase.updateProduct(product: Product) {

    update(ENTRIES_TABLE,
            ROW_NAME to product.name,
            ROW_PRICE to product.price,
            ROW_QUANTITY to product.quantity)
            .where("$ROW_ID = {id}", "id" to product.id).exec()
}


fun SQLiteDatabase.getProduct(id: Long): Product =
        select(ENTRIES_TABLE)
                .where("$ROW_ID = {id}", "id" to id)
                .exec { parseSingle(Product.parser) }


fun SQLiteDatabase.getEntries(query: String? = null): List<Product> =
        when (query) {
            null -> select(ENTRIES_TABLE).parseList(Product.parser)
            else -> select(ENTRIES_TABLE)
                    .where("$ROW_NAME like {query} ",
                            "query" to "%$query%")
                    .parseList(Product.parser)
        }