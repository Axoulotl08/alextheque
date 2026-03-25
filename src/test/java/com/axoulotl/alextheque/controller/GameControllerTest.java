package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.TestContenerConfig;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO : Ajouter un SQL pour des consoles pour les tests
public class GameControllerTest extends TestContenerConfig {

    private static final String GAME = "/api/v1/game";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ConsoleRepository consoleRepository;

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads(){
    }

    @BeforeEach
    void addConsole(){
        Console console = Console.builder()
                .zone(Zone.JAP)
                .name("Console")
                .manufacturer("Manuf")
                .launchDate(LocalDateTime.now().minusYears(3))
                .build();

        consoleRepository.save(console);
    }

    @Test
    public void whenAddGame_GivenGoodDTO_thenRespondWith200() throws Exception {
        Console console = consoleRepository.findAll().getFirst();
        GameDTO gameDTO = new GameDTO("TestName", console.getId(), Boolean.TRUE);

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(GAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.console.name").value(console.getName()));
    }

    @Test
    public void whenAddGame_GivenBlankName_thenResponseWith4XX() throws Exception{
        Console console = consoleRepository.findAll().getFirst();
        GameDTO gameDTO = new GameDTO("", console.getId(), Boolean.TRUE);

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(GAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenGetGame_GivenPageAndPageSize_thenResponseWith2XX() throws Exception{
        Console console = UtilsTest.createConsole(1);
        consoleRepository.save(console);

        for(int i = 0; i <= 5; i++){
            gameRepository.save(UtilsTest.createGameWithConsole(i, console));
        }

        int page = 0;
        int size = 2;

        this.mockMvc.perform(get(GAME)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenGetGame_GivenId_thenResponseWith2XX() throws Exception{
        Integer gameId = 1;
        Console console = consoleRepository.save(UtilsTest.createConsole(gameId));
        gameRepository.save(UtilsTest.createGameWithConsole(gameId, console));

        this.mockMvc.perform(get(GAME +"/" + gameId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(gameId));
    }
}
