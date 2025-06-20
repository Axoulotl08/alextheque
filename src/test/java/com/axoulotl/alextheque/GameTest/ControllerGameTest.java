package com.axoulotl.alextheque.GameTest;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ControllerGameTest extends TestContenerTestConfig {

    private static final String ADD_GAME = "/api/v1/game";

    @Autowired
    GameRepository gameRepository;

    MockMvc mockMvc;

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

        mockMvc.perform(post(ADD_GAME).content())
                .andDo(print())
                .andExpect();

    }
}
