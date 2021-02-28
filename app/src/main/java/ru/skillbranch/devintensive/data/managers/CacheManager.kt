package ru.skillbranch.devintensive.data.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLivaData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {

    private val chats = mutableLivaData(DataGenerator.stabChats)

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }
}