package com.erindavide.listaspesa.fragments

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erindavide.listaspesa.R
import kotlinx.android.synthetic.main.fragment_new_item_to_buy.*

/**
 * Created by demiurgo on 5/25/16.
 */


class NewGroceryItemBottomSheetFragment: BottomSheetDialogFragment(){

    var mCallback: OnGroceryItemCreation? = null

    // Container Activity must implement this interface
    interface OnGroceryItemCreation {
        fun onCreateGroceryItem(value: String)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity
        try{
            mCallback = activity as OnGroceryItemCreation
        }catch(e: ClassCastException){
            throw ClassCastException("$activity must implement OnHeadlineSelectedListener");
        }
    }


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if(inflater != null){
            return inflater.inflate(R.layout.fragment_new_item_to_buy,
                                    container,
                                    false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_create.setOnClickListener {
            if (edit_name.text.isNotBlank()) {
                mCallback?.onCreateGroceryItem(edit_name.text.toString())
                this.dismiss()
            }
        }
    }
}