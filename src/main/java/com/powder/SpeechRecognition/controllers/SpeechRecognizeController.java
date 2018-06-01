package com.powder.SpeechRecognition.controllers;

import com.google.api.client.util.Base64;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import com.powder.SpeechRecognition.AudioFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class SpeechRecognizeController {
    @ResponseBody
    @PostMapping(value = "/recognizespeech", consumes = "application/json", produces = "application/json")
    public String recognizeSpeech(@RequestBody AudioFile audioFile){

        String stringResponse = "";

        try (SpeechClient speechClient = SpeechClient.create()) {


            byte[] bytes = Base64.decodeBase64(audioFile.encodedBytes);

            InputStream is = new ByteArrayInputStream(bytes);

            byte[] data = new byte[is.available()];
            is.read(data);
            ByteString audioBytes = ByteString.copyFrom(data);

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("en-US")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                stringResponse = alternative.getTranscript();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringResponse;
    }
}
