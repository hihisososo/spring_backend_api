package com.lyj.backend.divider.controller;

import com.lyj.backend.divider.domain.DivideResult;
import com.lyj.backend.divider.service.HttpResponseDividerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response-divider")
public class HttpResponseDividerController {
    public HttpResponseDividerService htmlDividerService;

    @Autowired
    public void setHtmlDividerService(HttpResponseDividerService htmlDividerService) {
        this.htmlDividerService = htmlDividerService;
    }

    @GetMapping
    public ResponseEntity<DivideResult> getDivideResult(@RequestParam String url, @RequestParam String type, @RequestParam int printUnit) {
        return ResponseEntity.ok().body(htmlDividerService.getDivideResult(url, type, printUnit));
    }
}
