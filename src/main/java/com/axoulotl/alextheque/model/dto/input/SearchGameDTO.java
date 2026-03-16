package com.axoulotl.alextheque.model.dto.input;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchGameDTO {

    Integer consoleId;
    String name;
    LocalDate startedAfter;
    Integer statusId;
}
