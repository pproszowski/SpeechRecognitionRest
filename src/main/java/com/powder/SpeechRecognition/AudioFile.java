package com.powder.SpeechRecognition;

public class AudioFile {

    public String encodedBytes;

    public AudioFile() {

    }

    public AudioFile(String name, String encodedBytes) {
        this.encodedBytes = encodedBytes;
    }
}
