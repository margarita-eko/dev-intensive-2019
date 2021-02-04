package ru.skillbranch.devintensive

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardClosed
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.extensions.showKeyboard
import ru.skillbranch.devintensive.models.Bender


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        messageEt.setText(savedInstanceState?.getString("MESSAGE",""))
        messageEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                onClick(sendBtn)
                this.hideKeyboard()
                true
            }else{
                false
            }
        }

        messageEt.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){
                //Log.d("KEYBOARD", this.isKeyboardOpen().toString())
                onClick(sendBtn)
                this.hideKeyboard()
                true
            }else{
                false
            }

        }
        
        val(r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = savedInstanceState?.getString("QUESTION_TEXT") ?: benderObj.askQuestion()

        sendBtn.setOnClickListener(this)

        /*val isKeyboardOpen = savedInstanceState?.getBoolean("KEYBOARD_IS_OPEN") ?: false

        if (isKeyboardOpen && isKeyboardClosed()){
            //this.showKeyboard()
            if (messageEt.requestFocus()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(messageEt, InputMethodManager.SHOW_IMPLICIT)
                Log.d("KEYBOARD", this.isKeyboardOpen().toString())
            }
        }*/

        Log.d("KEYBOARD", this.isKeyboardOpen().toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS",benderObj.status.name)
        outState?.putString("QUESTION_TEXT",textTxt.text.toString())
        outState?.putString("QUESTION",benderObj.question.name)
        outState?.putString("MESSAGE",messageEt.text.toString())
        outState?.putBoolean("KEYBOARD_IS_OPEN",isKeyboardOpen())

    }
    override fun onClick(v: View?) {
        if(v?.id == R.id.iv_send){
            val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
            messageEt.setText("")
            val(r,g,b) = color
            benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase
            this.hideKeyboard()
        }
    }
}
