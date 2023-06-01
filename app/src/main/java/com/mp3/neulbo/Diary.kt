package com.mp3.neulbo

class Diary {
    var content: String?= null
    var Date: String?= null
    var isPublic: Boolean?= null
    var emotion: String?= null

    constructor(content: String?, Date: String?, isPublic: Boolean?, emotion: String?) {
        this.content = content
        this.Date = Date
        this.isPublic = isPublic
        this.emotion = emotion
    }
    fun getContent(content: String): String{
        return content
    }
    /*fun setContent(content: String?){
        this.content = content
    }*/

    fun toMap(): Map<String, Any?>{
        val result = HashMap<String, Any?>()
        result["content"] = content
        result["Date"] = Date
        return result
    }
    override fun toString(): String {
        return "Diary{content='$content', Date='$Date'}"
    }


}