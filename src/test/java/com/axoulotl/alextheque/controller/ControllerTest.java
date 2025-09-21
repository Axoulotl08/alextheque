package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.services.utils.UtilsTest;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ControllerTest extends TestContenerTestConfig {

    private static final String CONSOLE = "/api/v1/console";
    private static final String GAME = "/api/v1/game";
    private static final String SEARCH = "/api/v1/search";

    @Autowired
    ConsoleRepository consoleRepository;

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
        LocalDateTime now = LocalDateTime.now().minusYears(1L).truncatedTo(ChronoUnit.MICROS);

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setName("TestConsoleName");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(now);
        consoleDTO.setManufacturer("TestManuf");

        String json = mapper.writeValueAsString(consoleDTO);

        this.mockMvc.perform(post(CONSOLE)
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

        this.mockMvc.perform(post(CONSOLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("The name should not be empty of null."))
                .andExpect(jsonPath("$.typeError").value("ERROR_INPUT"));

    }

    @Test
    public void whenGetConsole_thenRespondWith200() throws Exception {
        LocalDateTime nowMinusOneHour = LocalDateTime.now().minusHours(1L).truncatedTo(ChronoUnit.MILLIS);

        Console console1 = new Console();
        console1.setName("Name");
        console1.setManufacturer("Manuf");
        console1.setZone(Zone.JAP);
        console1.setLaunchDate(nowMinusOneHour);

        Console console2 = new Console();
        console2.setName("Name2");
        console2.setManufacturer("Manuf2");
        console2.setZone(Zone.EUR);
        console2.setLaunchDate(nowMinusOneHour);

        consoleRepository.save(console1);
        consoleRepository.save(console2);

        this.mockMvc.perform(get(CONSOLE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Name"))
                .andExpect(jsonPath("$[1].manufacturer").value("Manuf2"));
    }

    @Test
    public void whenAddGame_GivenGoodDTO_thenRespondWith200() throws Exception {
        Console console = consoleRepository.save(UtilsTest.createConsole(1));
        GameDTO gameDTO = new GameDTO();
        gameDTO.setInbox(true);
        gameDTO.setName("TestName");
        gameDTO.setConsole(console.getId());

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
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName("");
        gameDTO.setInbox(true);

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

        this.mockMvc.perform(get(GAME + "/" + gameId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(gameId));
    }
}
