package com.erindavide.listaspesa.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.erindavide.listaspesa.R
import kotlinx.android.synthetic.main.activity_new_item_to_buy.*
import org.jetbrains.anko.onClick


class NewItemToBuyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item_to_buy)


        button_create.onClick {
            if (edit_name.text.isNotBlank()) {
                val intent = Intent()
                intent.putExtra(Extras.GROCERY_ITEM_DATA, edit_name.text.toString())
                setResult(RESULT_OK, intent)
                finishAfterTransition()
            }
        }

    }
}
