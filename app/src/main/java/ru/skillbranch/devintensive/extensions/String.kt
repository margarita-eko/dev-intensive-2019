package ru.skillbranch.devintensive.extensions

import android.R.attr.x


fun String.truncate(length: Int=16): String{
    return if (this.length <= length){
        this.trimIndent()
    }else{
        var stringTruncated = this.dropLast(this.length - length).dropLastWhile{
            it == ' '
        }
        stringTruncated+="..."
        stringTruncated
    }
}

fun String.stripHtml():String{
    var formatString = this
    val tagStart = '<'
    val tagEnd = '>'
    //remove html tags
    while (formatString.contains('<')){
        formatString = formatString.replace("$tagStart${formatString.substringAfter(tagStart).substringBefore(tagEnd)}$tagEnd","")
    }
    //remove seq
    val escapeSeq = mapOf<String,String>("&amp;" to "&", "&lt;" to "<", "&gt;" to ">", "&quot;" to "\"","&apos;" to "'")
    escapeSeq.forEach {
        formatString = formatString.replace(it.key,it.value)
    }
    // spaces
    while (formatString.contains("  ")) {
        formatString = formatString.replace("  ", " ")
    }
    return formatString
}
