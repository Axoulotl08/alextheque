package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.TestContenerConfig;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Console Controller Test")
public class ConsoleControllerTest extends TestContenerConfig {

    private static final String CONSOLE = "/api/v1/console";

    @Autowired
    ConsoleRepository consoleRepository;

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
    @DisplayName("Everything is OK : 200")
    public void whenAddConsole_GivenGoodDTO_thenRespondWith200() throws Exception {
        LocalDateTime now = LocalDateTime.now().minusYears(1L).truncatedTo(ChronoUnit.MICROS);

        ConsoleDTO consoleDTO = new ConsoleDTO("TestConsoleName", "TestManuf", now, 1);

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
    @DisplayName("Console name is wring : 400")
    public void whenAddConsole_GivenWrongName_thenRespondWith400() throws Exception{
        LocalDateTime now = LocalDateTime.now().minusYears(1L).truncatedTo(ChronoUnit.MICROS);;

        ConsoleDTO consoleDTO = new ConsoleDTO("", "TestManuf", now, 1);


        String json = mapper.writeValueAsString(consoleDTO);

        this.mockMvc.perform(post(CONSOLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Validation Error"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.error.length()").value(1))
                .andExpect(jsonPath("$.error.name").value("The console name should not be null"));

    }

    @Test
    @DisplayName("Get All Console")
    @Sql(scripts = "/sql/init-console.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
}
