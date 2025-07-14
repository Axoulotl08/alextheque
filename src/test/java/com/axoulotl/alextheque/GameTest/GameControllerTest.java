package com.axoulotl.alextheque.GameTest;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GameControllerTest extends TestContenerTestConfig {

    private static final String ADD_GAME = "/api/v1/game";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ConsoleRepository consoleRepository;

    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

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
    public void testControllerGame() {
        Console console = consoleRepository.findAll().getFirst();

        Game game = new Game();
        game.setName("Test");
        game.setInbox(Boolean.TRUE);
        game.setConsole(console);
        gameRepository.save(game);

        List<Game> retrieve = gameRepository.findAll();
        Assertions.assertThat(retrieve).hasSize(1);
    }

    @Test
    public void whenAddGame_GivenGoodDTO_thenRespondWith200() throws Exception {
        Console console = consoleRepository.findAll().getFirst();
        GameDTO gameDTO = new GameDTO();
        gameDTO.setInbox(true);
        gameDTO.setName("TestName");
        gameDTO.setConsole(console.getId());

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(ADD_GAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.console.name").value(console.getName()));
    }

    @Test
    public void whenAddGame_GivenBlankName_thenResponseWith4XX() throws Exception{
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName("");
        gameDTO.setInbox(true);

        String json = mapper.writeValueAsString(gameDTO);

        this.mockMvc.perform(post(ADD_GAME)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
