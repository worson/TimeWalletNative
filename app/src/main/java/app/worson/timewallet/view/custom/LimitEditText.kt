package app.worson.timewallet.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import app.worson.timewallet.test.page.notta.SmartNoteItem


/**
 * created by canxionglian on 2019-11-22
 */
class LimitEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection? {
        val inputConnection: InputConnection? = super.onCreateInputConnection(outAttrs)

        return if (null == inputConnection) {
            null
        } else {
            InnerInputConnection(
                super.onCreateInputConnection(outAttrs),
                false,
                object : InputConnectionHandler {
                    override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
                        return inputConnectionHandler?.commitText(text, newCursorPosition) ?: false
                    }

                    override fun sendKeyEvent(event: KeyEvent): Boolean {
                        return inputConnectionHandler?.sendKeyEvent(event) ?: false
                    }

                    override fun setSelection(start: Int, end: Int): Boolean {
                        return inputConnectionHandler?.setSelection(start, end) ?: false
                    }

                })
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isEnabled = false
        isEnabled = true
    }

    var inputConnectionHandler: InputConnectionHandler? = null

}


interface InputConnectionHandler {

    fun commitText(text: CharSequence, newCursorPosition: Int): Boolean

    fun sendKeyEvent(event: KeyEvent): Boolean

    fun setSelection(start: Int, end: Int): Boolean
}

open class DefaultInputHandler : InputConnectionHandler, TextWatcher{
    override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
        return false
    }

    override fun sendKeyEvent(event: KeyEvent): Boolean {
        return false
    }

    override fun setSelection(start: Int, end: Int): Boolean {
        return true
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

class InnerInputConnection(
    target: InputConnection,
    mutable: Boolean,
    private val inputConnectionHandler: InputConnectionHandler
) :
    InputConnectionWrapper(target, mutable) {


    /**
     * 对输入的内容进行拦截
     *
     * @param text
     * @param newCursorPosition
     * @return
     */
    override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
        if (text.length == 1 && text[0] == '\n') {
            sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            return true
        }
        return if (inputConnectionHandler.commitText(text, newCursorPosition)) {
            false
        } else super.commitText(text, newCursorPosition)
    }

    override fun sendKeyEvent(event: KeyEvent): Boolean {
        return if (inputConnectionHandler.sendKeyEvent(event)) false else super.sendKeyEvent(event)
    }

    override fun setSelection(start: Int, end: Int): Boolean {
        return if (inputConnectionHandler.setSelection(start, end)) false else super.setSelection(
            start,
            end
        )
    }


}