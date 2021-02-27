package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLivaData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel() : ViewModel() {

    private val chatRepository = ChatRepository
    private val chats = mutableLivaData(loadChats())

    fun getChatData(): LiveData<List<ChatItem>> {
        return chats
    }

    private fun loadChats(): List<ChatItem>? {
        val chats = chatRepository.loadChats()
        return chats.map {
            it.toChatItem()
        }.sortedBy { it.id.toInt() }

    }

    fun addItems() {
        val newItems = DataGenerator.generateChatsWithOffset(chats.value!!.size,5).map { it.toChatItem() }
        val copy = chats.value!!.toMutableList()
        copy.addAll(newItems)
        chats.value = copy.sortedBy { it.id.toInt() }
    }

    fun addToArchive(id: String) {

    }


}