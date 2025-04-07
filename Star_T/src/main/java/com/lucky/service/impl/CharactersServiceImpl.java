package com.lucky.service.impl;

import com.lucky.mapper.CharactersMapper;
import com.lucky.service.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CharactersServiceImpl implements CharactersService {
    @Autowired
    private CharactersMapper charactersMapper;

    @Override
    public List<Map<String, Object>> countCharactersByRarity() {
        return charactersMapper.countCharactersByRarity();
    }
}
