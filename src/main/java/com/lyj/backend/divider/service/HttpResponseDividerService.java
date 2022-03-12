package com.lyj.backend.divider.service;

import com.lyj.backend.divider.domain.Type;
import com.lyj.backend.divider.domain.DivideResult;

public interface HttpResponseDividerService {
    public DivideResult getDivideResult(String url, Enum<Type> type, int printUnit);
}
