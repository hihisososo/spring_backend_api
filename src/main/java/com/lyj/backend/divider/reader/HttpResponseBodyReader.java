package com.lyj.backend.divider.reader;

import com.lyj.backend.divider.exception.InvalidUrlException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HttpResponseBodyReader implements ResponseBodyReader {
    @Override
    public String read(String url) {
        URI requestUrl = null;
        try {
            requestUrl = URI.create(url);
        } catch (IllegalArgumentException e) {
            throw new InvalidUrlException("invalid url", e);
        }

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(requestUrl).build();
            HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch (IOException | InterruptedException e) {
            throw new ResponseBodyReadFailException("response body read fail", e);
        }
    }
}
