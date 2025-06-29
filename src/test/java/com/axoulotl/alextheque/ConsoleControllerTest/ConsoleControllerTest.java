package com.axoulotl.alextheque.ConsoleControllerTest;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Charge le contexte Spring Boot complet
@AutoConfigureMockMvc
@Transactional
public class ConsoleControllerTest extends TestContenerTestConfig {

    private static final String ADD_CONSOLE = "/api/v1/console";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void contextLoads(){
    }

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void whenAddConsole_GivenGoodDTO_thenRespondWith200() throws Exception {
        LocalDateTime now = LocalDateTime.now().minusYears(1L).truncatedTo(ChronoUnit.MICROS);;

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setName("TestConsoleName");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(now);
        consoleDTO.setManufacturer("TestManuf");

        String json = mapper.writeValueAsString(consoleDTO);

        this.mockMvc.perform(post(ADD_CONSOLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("TestConsoleName"))
                .andExpect(jsonPath("$.manufacturer").value("TestManuf"))
                .andExpect(jsonPath("$.zone").isString())
                .andExpect(jsonPath("$.zone").value("JAP"))
                .andExpect(jsonPath("$.launchDate").value(now.toString()));
    }

    @Test
    public void whenAddConsole_GivenWrongName_thenRespondWith400() throws Exception{
        LocalDateTime now = LocalDateTime.now().minusYears(1L).truncatedTo(ChronoUnit.MICROS);;

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setName("");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(now);
        consoleDTO.setManufacturer("TestManuf");

        String json = mapper.writeValueAsString(consoleDTO);

        this.mockMvc.perform(post(ADD_CONSOLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("The name should not be empty of null."))
                .andExpect(jsonPath("$.typeError").value("ERROR_INPUT"));

    }
}
