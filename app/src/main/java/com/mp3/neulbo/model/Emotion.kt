package com.mp3.neulbo.model

// 감정 데이터 기본 틀
data class Emotion (
    var happiness: Double = 0.0,
    var neutrality: Double = 0.0,
    var sadness: Double = 0.0,
    var worry: Double = 0.0,
    var anger: Double = 0.0
)