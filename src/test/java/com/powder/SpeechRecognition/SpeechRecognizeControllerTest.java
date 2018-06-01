package com.powder.SpeechRecognition;

import com.google.api.client.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpeechRecognizeControllerTest {

    @Test
    public void shouldReceiveHowOldIsTheBrooklynBridgeAnswer(){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("audio.raw");

        byte[] data;
        try {
            data = new byte[is.available()];
            is.read(data);

            String data64 = Base64.encodeBase64String(data);

            JSONObject jsonObject = new JSONObject()
                    .put("name", "audio.raw")
                    .put("encodedBytes", data64);

            HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

            try {
                HttpPost request = new HttpPost("http://localhost:8080/recognizespeech");
                StringEntity params =new StringEntity(jsonObject.toString());
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                HttpResponse response = httpClient.execute(request);

                String content = EntityUtils.toString(response.getEntity());
                Assert.assertEquals("how old is the Brooklyn Bridge", content);

            }catch (Exception ex) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
