package com.lucky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CharactersMapper{
    List<Map<String, Object>> countCharactersByRarity();
}
