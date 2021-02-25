package ru.skillbranch.devintensive.models.myclasses

import ru.skillbranch.devintensive.models.myclasses.BaseMessage
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1
        fun makeMessage(from: User, chat:Chat, date:Date, type:String, payload: Any?, isIncoming:Boolean = false): BaseMessage {
            lastId++
            return when (type) {
                "image" -> {
                    ImageMessage(
                        "$lastId",
                        from,
                        chat,
                        isIncoming,
                        date,
                        image = payload as String
                    )
                    //println(message.formatMessage())
                }

                else -> {
                    TextMessage(
                        "$lastId",
                        from,
                        chat,
                        isIncoming,
                        date,
                        text = payload as String
                    )
                    //println(message.formatMessage())
                }
            }
        }
    }
}