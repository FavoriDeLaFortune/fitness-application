package com.example.fitnessapplication.ui.calendar

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.fitnessapplication.R

class CalendarDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESPONSE to which))
        }
        return AlertDialog.Builder(requireContext())
            .setCancelable(true).setIcon(R.mipmap.ic_launcher_round)
            .setTitle("dsad")
            .setMessage("dsa")
            .setPositiveButton("YES", listener)
            .setNegativeButton("NO", listener)
            .setNeutralButton("IGNORE", listener)
            .create()
    }
    companion object {
        @JvmStatic val TAG = CalendarDialogFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic val KEY_RESPONSE = "RESPONSE"
    }
}