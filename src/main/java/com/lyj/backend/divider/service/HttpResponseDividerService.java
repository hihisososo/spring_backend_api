package com.lyj.backend.divider.service;

import com.lyj.backend.divider.domain.DivideResult;

public interface HttpResponseDividerService {
    public DivideResult getDivideResult(String url, String type, int printUnit);
}
