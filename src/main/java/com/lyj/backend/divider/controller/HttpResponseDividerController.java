package com.lyj.backend.divider.controller;

import com.lyj.backend.divider.domain.DivideResult;
import com.lyj.backend.divider.domain.Type;
import com.lyj.backend.divider.service.HttpResponseDividerService;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "URL 파싱 후 데이터 가공", notes = "URL, 출력타입, 출력단위를 받아 몫과 나머지를 출력합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "http 요청 URL", required = true),
            @ApiImplicitParam(name = "type", value = "출력 타입", required = true),
            @ApiImplicitParam(name = "printUnit", value = "출력 단위", required = true)})
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 파라미터"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ResponseEntity<DivideResult> getDivideResult(@RequestParam String url, @RequestParam("type") Type type, @RequestParam int printUnit) {
        return ResponseEntity.ok().body(htmlDividerService.getDivideResult(url, type, printUnit));
    }
}
