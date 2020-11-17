package com.fzufood.service;

import com.fzufood.dto.JsonObject;
import com.fzufood.dto.SystemInfo;


public interface SystemService {
    JsonObject<SystemInfo> getInfo();
}
