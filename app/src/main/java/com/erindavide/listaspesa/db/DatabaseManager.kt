package com.erindavide.listaspesa.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.erindavide.listaspesa.model.Product
import org.jetbrains.anko.db.*

/**
 * Created by linnal on 6/2/16.
 */


const val ENTRIES_TABLE = "product"
const val ROW_ID = "_id"
const val ROW_NAME = "name"
const val ROW_PRICE = "price"
const val ROW_QUANTITY = "quantity"
private const val TAG = "DatabaseManager"


class DatabaseManager(context: Context) : ManagedSQLiteOpenHelper(context, "ShareShopDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(ENTRIES_TABLE, true, // IF_NOT_EXISTS
                ROW_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ROW_NAME to TEXT + NOT_NULL,
                ROW_PRICE to REAL,
                ROW_QUANTITY to INTEGER
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


fun SQLiteDatabase.insertProduct(product: Product): Long = insert(ENTRIES_TABLE,
        ROW_NAME to product.name,
        ROW_PRICE to product.price,
        ROW_QUANTITY to product.quantity
)

fun SQLiteDatabase.reinsertProduct(product: Product): Long = insert(ENTRIES_TABLE,
        ROW_ID to product.id,
        ROW_NAME to product.name,
        ROW_PRICE to product.price,
        ROW_QUANTITY to product.quantity
)


fun SQLiteDatabase.updateProduct(product: Product) {

    update(ENTRIES_TABLE,
            ROW_NAME to product.name,
            ROW_PRICE to product.price,
            ROW_QUANTITY to product.quantity)
            .whereArgs("$ROW_ID = {id}", "id" to product.id).exec()
}


fun SQLiteDatabase.getProduct(id: Long): Product =
        select(ENTRIES_TABLE)
                .whereArgs("$ROW_ID = {id}", "id" to id)
                .exec { parseSingle(Product.parser) }

fun SQLiteDatabase.deleteProduct(id: Long) : Product{
    // TODO maybe use a transaction in here
    val p = getProduct(id)
    delete(ENTRIES_TABLE, "$ROW_ID = {id}", "id" to id)
    return p
}


fun SQLiteDatabase.getProductsIdList(): MutableList<Long> =
        select(ENTRIES_TABLE, ROW_ID).parseList(LongParser).toMutableList()

fun SQLiteDatabase.getEntries(query: String? = null): List<Product> =
        when (query) {
            null -> select(ENTRIES_TABLE).parseList(Product.parser)
            else -> select(ENTRIES_TABLE)
                    .whereArgs("$ROW_NAME like {query} ",
                            "query" to "%$query%")
                    .parseList(Product.parser)
        }