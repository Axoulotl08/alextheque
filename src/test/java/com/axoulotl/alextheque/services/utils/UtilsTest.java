package com.axoulotl.alextheque.services.utils;

import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Zone;

import java.time.LocalDateTime;

public class UtilsTest{

    public static Game createGame(Integer id){
        return Game.builder()
                .name("Test" + id)
                .id(id)
                .inbox(true)
                .console(createConsole(id))
                .creationDate(LocalDateTime.now().minusHours(2))
                .build();
    }

    public static Game createGameWithConsole(Integer id, Console console){
        return Game.builder()
                .name("Test" + id)
                .id(id)
                .inbox(true)
                .console(console)
                .creationDate(LocalDateTime.now().minusHours(2))
                .build();
    }

    public static Console createConsole(Integer id){
        return Console.builder()
                .name("Console" + id)
                .manufacturer("Manuf" + id)
                .launchDate(LocalDateTime.now().minusYears(1))
                .zone(Zone.JAP)
                .creationDate(LocalDateTime.now().minusHours(3))
                .build();
    }
}
