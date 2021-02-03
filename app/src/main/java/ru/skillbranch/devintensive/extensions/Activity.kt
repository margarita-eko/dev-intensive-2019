package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.R


fun Activity.hideKeyboard(){
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen(rootView: View):Boolean{
    return keyBoardIsOpen(this,rootView)
}

fun Activity.isKeyboardClose(rootView: View):Boolean{
    return !keyBoardIsOpen(this,rootView)
}

private fun keyBoardIsOpen(activity: Activity,contentView: View): Boolean{
    val r = Rect()
    //val contentView = activity
    contentView.getWindowVisibleDisplayFrame(r)
    val screenHeight: Int = contentView.rootView.height
    // r.bottom is the position above soft keypad or device button.
    // if keypad is shown, the r.bottom is smaller than that before.
    val keypadHeight: Int = screenHeight - r.bottom
    return if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
        true
    } else {
        false
    }
}
