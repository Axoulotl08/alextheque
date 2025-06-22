package com.axoulotl.alextheque.ConsoleControllerTest;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Charge le contexte Spring Boot complet
@AutoConfigureMockMvc
@Transactional
public class ConsoleControllerTest extends TestContenerTestConfig {

    private static final String ADD_GAME = "/api/v1/game";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads(){

    }

    @Test
    public void testControllerGame() {
        Game game = new Game();
        game.setName("Test");
        game.setConsole("Test");
        game.setInbox(Boolean.TRUE);
        gameRepository.save(game);

        List<Game> retrieve = gameRepository.findAll();
        Assertions.assertThat(retrieve).hasSize(1);
    }

    @Test
    public void whenAddGame_GivenGoodDTO_thenRespondWith200() throws Exception {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setConsole("TestConsole");
        gameDTO.setInbox(true);
        gameDTO.setName("TestName");

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(ADD_GAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.console").value("TestConsole"));
    }

    @Test
    public void whenAddGame_GivenBlankName_thenResponseWith4XX() throws Exception{
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName("");
        gameDTO.setInbox(true);
        gameDTO.setConsole("Testconsole");

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(ADD_GAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
