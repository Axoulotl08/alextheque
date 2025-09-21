package com.axoulotl.alextheque.model.dto.input;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class SearchGameDTO {

    Integer consoleId;
    String name;
    LocalDate startedAfter;
    Integer statusId;
}
