import pandas as pd
import numpy as np
import tensorflow as tf
from konlpy.tag import *
import re
import matplotlib.pyplot as plt
from tensorflow import keras
from keras import layers
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

import os
from sklearn.preprocessing import LabelEncoder, OneHotEncoder


from flask import Flask, jsonify, json
from flask_restful import Resource, Api, reqparse, abort

# Flask 인스턴스 정리

app = Flask(__name__)

api = Api(app)


df = pd.read_csv('data/most_common.csv')

result_emotion = {0:"공포", 1:"분노", 2:"슬픔", 3:"중립", 
                 4:"행복", 5:"혐오"}

ohe = OneHotEncoder(sparse=False)
result = df['emotion']

ohe.fit(result.values.reshape(-1, 1))
one_hot_encoded = ohe.transform(result.values.reshape(-1, 1))
one_hot_encoded

train_Y = one_hot_encoded
train_Y

sentences = list(df['speech'].values)
sentences = [re.sub(r'[^가-힣A-Za-z0-9]', ' ', sentence) for sentence in sentences]
sentences = [re.sub(r'\s+', ' ', sentence) for sentence in sentences]

# han = Hannanum()
okt = Okt()
# sentences = [han.morphs(sentence) for sentence in sentences]
sentences = [okt.morphs(sentence) for sentence in sentences]
sentences

sentences_new = []
for sentence in sentences:
    sentences_new.append([word for word in sentence][:8])
sentences = sentences_new

tokenizer = Tokenizer(num_words=30000)
tokenizer.fit_on_texts(sentences)
train_X = tokenizer.texts_to_sequences(sentences)
train_X = pad_sequences(train_X, padding='post', maxlen=20)


model = keras.models.load_model('modelsave/emotion_detect.h5',compile=False)#


@app.route('/echo_call/<param>') #get echo api
def get_echo_call(param):
    print(param)
    test_sentence = param.split(' ')

    test_sentences = []
    now_sentence = []
    for word in test_sentence:
        now_sentence.append(word)
        test_sentences.append(now_sentence[:])


    test_X_1 = tokenizer.texts_to_sequences(test_sentences)
    test_X_1 = pad_sequences(test_X_1, padding='post', maxlen=20)

    prediction = model.predict(test_X_1)

    for idx, sentence in enumerate(test_sentences):
        print(sentence)
        print(prediction[idx])
        result = prediction[idx]
        res_ind = np.argmax(result)

    print("감정분석 결과: ", result_emotion[res_ind])

    print(result_emotion[res_ind])

    ans = result_emotion[res_ind]
    ans = ans[:ans.find(".")]

    # return result_emotion[res_ind]

    return json.dumps({"param":result_emotion[res_ind]})
    return jsonify({"param": ans}) # 모델의 예측 결과를 JSON 형태로 반환
    

if __name__ == '__main__':
    app.run(host="172.16.231.152", port=5000, debug=True)