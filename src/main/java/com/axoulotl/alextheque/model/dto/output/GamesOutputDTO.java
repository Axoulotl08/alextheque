package com.axoulotl.alextheque.model.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GamesOutputDTO {
    private Long nbGames;
    private Integer nbPages;
    private List<GameOutputDTO> games;
}
