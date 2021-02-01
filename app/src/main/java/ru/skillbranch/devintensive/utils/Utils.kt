package ru.skillbranch.devintensive.utils

object Utils {

    private val translitarationDictionary = mapOf<String,String>(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
   )

    fun parseFullName(fullName:String?):Pair<String?, String?>{

        var firstName: String? = null
        var lastName: String? = null

        if (!fullName.isNullOrBlank()) {
            val parts: List<String>? = fullName?.split(" ")
            firstName = parts?.getOrNull(0)
            lastName  = parts?.getOrNull(1)
        }

        return firstName to lastName

    }

    fun toInitials(firstName:String?, lastName:String?):String? {
        val fnInitial = getInitial(firstName)
        val lnInitial = getInitial(lastName)
        return if (fnInitial.isEmpty() && lnInitial.isEmpty()) {
            null
        }else{
            "$fnInitial$lnInitial"
        }
    }

    private fun getInitial(text: String?):String{
        return if (text.isNullOrBlank()){
            ""
        }else{
            text[0].toUpperCase().toString()
        }
    }

    fun transliteration(payload:String, divider:String = " "):String{
        var transliterationText = ""
        payload.forEach {
            if (it.toString()==" ") {
                transliterationText += divider
            }else{
                val mapKey = it.toLowerCase().toString()
                var newValue: String = it.toString()
                if (translitarationDictionary.containsKey(mapKey)) {
                    newValue = translitarationDictionary[mapKey].toString()
                    if (it.isUpperCase()) {
                        newValue = newValue.capitalize()
                    }
                }
                transliterationText += newValue
            }
        }
        return transliterationText
    }
}