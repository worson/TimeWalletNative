package com.worson.lib.appbasic.view.extend

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

/**
 * @author worson  10.25 2020
 */

fun EditText.enableEdit(){
    isLongClickable = false
    customSelectionActionModeCallback = null
    isCursorVisible = true
    showSoftInputOnFocus = true
}

fun EditText.disableEdit(){
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = false

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }
    isCursorVisible = false
    showSoftInputOnFocus = false
}

