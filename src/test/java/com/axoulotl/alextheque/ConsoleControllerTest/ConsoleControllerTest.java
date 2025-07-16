package com.axoulotl.alextheque.ConsoleControllerTest;

import com.axoulotl.alextheque.TestContenerTestConfig;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ConsoleControllerTest extends TestContenerTestConfig {

    private static final String CONSOLE = "/api/v1/console";

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
}
