package com.powder.SpeechRecognition;

public class AudioFile {

    public String name;

    public String encodedBytes;

    public AudioFile() {

    }

    public AudioFile(String name, String encodedBytes) {
        this.name = name;
        this.encodedBytes = encodedBytes;
    }
}
