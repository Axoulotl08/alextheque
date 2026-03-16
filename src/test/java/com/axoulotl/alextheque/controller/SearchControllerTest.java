package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.TestContenerConfig;
import com.axoulotl.alextheque.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class SearchControllerTest extends TestContenerConfig {
    private static final String SEARCH = "/api/v1/search/game";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {}

    @Test
    public void searchGameTest() throws Exception {}
}
