package com.lyj.backend.divider.service;

import com.lyj.backend.divider.dto.Type;
import com.lyj.backend.divider.dto.DivideResult;

public interface HttpResponseDividerService {
    public DivideResult getDivideResult(String url, Enum<Type> type, int printUnit);
}
