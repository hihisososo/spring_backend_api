package com.lyj.backend.divider.controller;

import com.lyj.backend.divider.dto.DivideRequest;
import com.lyj.backend.divider.dto.DivideResult;
import com.lyj.backend.divider.service.HttpResponseDividerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/response-divider")
@Slf4j
@RequiredArgsConstructor
public class HttpResponseDividerController {
    private final HttpResponseDividerService htmlDividerService;

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
    public ResponseEntity<DivideResult> getDivideResult(@Valid DivideRequest divideRequest) {
        log.debug("get /response-divider");
        return ResponseEntity.ok().body(htmlDividerService.getDivideResult(divideRequest));
    }

}
