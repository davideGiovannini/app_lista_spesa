package com.erindavide.listaspesa.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.erindavide.listaspesa.R
import com.erindavide.listaspesa.adapters.GroceryItemDecoration
import com.erindavide.listaspesa.adapters.GroceryItemTouchHelper
import com.erindavide.listaspesa.adapters.GroceryListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*



class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GroceryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            val intent = Intent(this, NewItemToBuyActivity::class.java)
            startActivityForResult(intent, Actions.NEW_GROCERY_ITEM)
        }

        setupRecycleView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Actions.NEW_GROCERY_ITEM -> {
                if(resultCode == RESULT_OK && data?.hasExtra(Extras.GROCERY_ITEM_DATA) ?: false){
                    adapter.addItem(data!!.getStringExtra(Extras.GROCERY_ITEM_DATA))
                }
            }
            else -> {
                Log.w(TAG, "Unmanaged request code in onActivityResult: $requestCode")
            }
        }

    }

    private fun setupRecycleView(){
        adapter = GroceryListAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(GroceryItemDecoration())

        val callback = GroceryItemTouchHelper(adapter)
        val mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(recycler_view)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
