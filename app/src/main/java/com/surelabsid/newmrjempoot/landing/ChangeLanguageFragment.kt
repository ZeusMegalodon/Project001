package com.surelabsid.newmrjempoot.landing

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surelabsid.newmrjempoot.R

class ChangeLanguageFragment : BottomSheetDialogFragment() {
    var behavior: BottomSheetBehavior<View>? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheets = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_change_language, null)
        val root = view.findViewById<LinearLayout>(R.id.root)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        root.layoutParams = params
        bottomSheets.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheets.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheet =
                (dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
        initView(view)
        return bottomSheets
    }

    private fun initView(view: View) {
        val close = view.findViewById<ImageView>(R.id.close)
        val indonesia = view.findViewById<LinearLayout>(R.id.indonesia)
        val china = view.findViewById<LinearLayout>(R.id.china)
        val english = view.findViewById<LinearLayout>(R.id.english)
        val arabic = view.findViewById<LinearLayout>(R.id.arabic)
        indonesia.setOnClickListener { }
        china.setOnClickListener { }
        english.setOnClickListener { }
        arabic.setOnClickListener { }

        close.setOnClickListener { v: View? -> dismiss() }
    }
}