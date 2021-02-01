package ru.skillbranch.devintensive.models

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
        fun makeMessage(from:User, chat:Chat, date:Date, type:String, payload: Any?, isIncoming:Boolean = false):Unit{
            lastId++
            return when (type) {
                "image" -> {
                    val message = ImageMessage(
                        "$lastId",
                        from,
                        chat,
                        isIncoming,
                        date,
                        image = payload as String
                    )
                    println(message.formatMessage())
                }

                else -> {
                    val message = TextMessage("$lastId", from, chat, isIncoming, date, text = payload as String)
                    println(message.formatMessage())
                }
            }
        }
    }
}