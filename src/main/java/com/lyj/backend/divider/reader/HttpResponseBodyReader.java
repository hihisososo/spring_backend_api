package com.lyj.backend.divider.reader;

import com.lyj.backend.divider.exception.InvalidUrlException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class HttpResponseBodyReader implements ResponseBodyReader {
    @Override
    public String read(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new InvalidUrlException("invalid url", e);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new ResponseBodyReadFailException("response body read fail", e);
        }
    }
}
