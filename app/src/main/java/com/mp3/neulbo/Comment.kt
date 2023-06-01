package com.mp3.neulbo

class Comment {
    var comment: String?= null
    var Date: String?= null

    constructor(comment: String?, Date: String?) {
        this.comment = comment
        this.Date = Date
    }
    fun getcomment(comment: String): String{
        return comment
    }
    /*fun setcomment(comment: String?){
        this.comment = comment
    }*/

    fun toMap(): Map<String, Any?>{
        val result = HashMap<String, Any?>()
        result["comment"] = comment
        result["Date"] = Date
        return result
    }
    override fun toString(): String {
        return "Diary{comment='$comment', Date='$Date'}"
    }


}