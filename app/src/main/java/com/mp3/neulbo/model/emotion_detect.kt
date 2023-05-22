package com.mp3.neulbo.model
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.util.common.model.Pair
import java.io.File

fun emotion_detect(text: String): String {
    // 감정 분석을 위해 EmoInt 파일을 로드합니다.
    val lexiconFile = File("emoint.txt")
    val lexicon = mutableMapOf<String, MutableMap<String, Double>>()
    lexiconFile.forEachLine { line ->
        val parts = line.split("\t")
        val word = parts[0]
        val emotion = parts[1]
        val value = parts[2].toDouble()
        if (!lexicon.containsKey(word)) {
            lexicon[word] = mutableMapOf()
        }
        lexicon[word]!![emotion] = value
    }

    // 한국어 형태소 분석기인 Komoran 객체를 초기화합니다.
    val komoran = Komoran()

    // 텍스트를 형태소 분석하여 단어 리스트를 생성합니다.
    val words = komoran.analyze(text).tokens
        .filter { it.pos == "NNG" || it.pos == "VV" || it.pos == "VA" }
        .map { it.morph }

    // 단어 리스트에 대한 감정 값을 계산합니다.
    val emotions = mutableMapOf<String, Double>()
    for (word in words) {
        val wordEmotions = lexicon[word]
        if (wordEmotions != null) {
            for ((emotion, value) in wordEmotions) {
                emotions[emotion] = emotions.getOrDefault(emotion, 0.0) + value
            }
        }
    }

    // 가장 높은 감정 값을 가진 감정을 반환합니다.
    return emotions.maxByOrNull { it.value }?.key ?: "Unknown"
}