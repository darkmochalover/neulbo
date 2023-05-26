package com.mp3.neulbo

class Diary {
    var content: String?= null

    constructor(content: String?) {
        this.content = content
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
        return result
    }
    override fun toString(): String {
        return "Diary{content='$content'}"
    }


}