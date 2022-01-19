package com.surelabsid.newmrjempoot.settings.akun.sheets

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.surelabsid.newmrjempoot.R
import com.surelabsid.newmrjempoot.settings.akun.AkunViewModel


class JenisKelaminBottomSheet : BottomSheetDialogFragment() {
    var behavior: BottomSheetBehavior<*>? = null
    private lateinit var akunViewModel: AkunViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheets = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_jenis_kelamin_bottom_sheet, null)
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
            behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        akunViewModel = ViewModelProvider(requireActivity()).get(AkunViewModel::class.java)

        initView(view)

        return bottomSheets
    }

    private fun initView(view: View) {
        val rg = view.findViewById<RadioGroup>(R.id.rdGroup)
        rg.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            when (i) {
                R.id.lakiLaki -> {
                    akunViewModel.getJk("Laki-Laki")
                    dismiss()
                }
                R.id.perempuan -> {
                    akunViewModel.getJk("Perempuan")
                    dismiss()
                }
                R.id.lainnya -> {
                    akunViewModel.getJk("Lainnya")
                    dismiss()
                }
                R.id.tidakSebutkan -> {
                    akunViewModel.getJk("Tidak Ingin Menyebutkan")
                    dismiss()
                }
            }
        }
    }
}