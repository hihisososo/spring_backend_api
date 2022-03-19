package com.lyj.backend.divider.service;

import com.lyj.backend.divider.dto.DivideRequest;
import com.lyj.backend.divider.dto.DivideResult;

public interface HttpResponseDividerService {
    public DivideResult getDivideResult(DivideRequest divideRequest);
}
