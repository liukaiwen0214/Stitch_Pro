package com.lucky.service;

import com.lucky.entity.GodEntity;

import java.util.List;
import java.util.Map;

public interface GodService {
    GodEntity getRandomGod();
    List<Map<String, Object>> getGodCount();
}
