package ru.skillbranch.devintensive.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.adapters.ChatAdapter
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {


    private val chatAdapter: ChatAdapter = ChatAdapter()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolBar()
        initViews()
        initViewModels()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getChatData()
    }

    private fun initViews() {
        with(rv_chat_list){
            adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

}
