package ru.skillbranch.devintensive.models

data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {

    val nickname = "John Doe"
    val rank: String = "Junior Android Developer"

    fun toMap(): Map<String, Any?>{
        return mapOf(
            "respect" to respect,
            "rank" to rank,
            "nickname" to nickname,
            "rating" to rating,
            "firstname" to firstName,
            "lastname" to lastName,
            "repository" to repository,
            "about" to about
        )
    }

}