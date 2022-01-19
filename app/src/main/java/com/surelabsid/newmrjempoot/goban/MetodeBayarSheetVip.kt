package com.surelabsid.newmrjempoot.goban

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surelabsid.newmrjempoot.R

class MetodeBayarSheetVip : BottomSheetDialogFragment() {
    var behavior: BottomSheetBehavior<*>? = null
    private val gobanViewModel: GobanViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheets = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_metode_bayar_sheet_vip, null)
        val root = view.findViewById<LinearLayout>(R.id.root)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        root.layoutParams = params

        bottomSheets.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)

        bottomSheets.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomsheet =
                (dialogInterface as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior =
                BottomSheetBehavior.from(bottomsheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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
        val rg = view.findViewById<RadioGroup>(R.id.rdGroup)
        rg.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            when (i) {
                R.id.cash -> {
                    gobanViewModel.setPayment(0, "Cash")
                }
                R.id.jempootPay -> {
                    gobanViewModel.setPayment(1, "Jempoot Pay")
                }
            }
        }

        view.findViewById<Button>(R.id.apply).setOnClickListener {
            dismiss()
        }

    }
}