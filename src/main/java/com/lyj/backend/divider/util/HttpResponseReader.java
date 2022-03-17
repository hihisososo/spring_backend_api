package com.lyj.backend.divider.util;

import com.lyj.backend.divider.exception.InvalidUrlException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class HttpResponseReader {
    public String read(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            return HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()).body().toString();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new InvalidUrlException("invalid url", e);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new ResponseBodyReadFailException("response body read fail", e);
        }
    }
}
