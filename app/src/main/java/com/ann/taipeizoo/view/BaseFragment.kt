package com.ann.taipeizoo.view

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ann.taipeizoo.R

open class BaseFragment : Fragment() {
    fun setActionBar(enable: Boolean, titleString: String? = getString(R.string.app_name)) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowHomeEnabled(enable)
            setDisplayHomeAsUpEnabled(enable)
            title = titleString
        }
    }
    fun showErrorDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder
            .setTitle(getString(R.string.dialog_title_error))
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.dialog_confirm)
            ) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}
