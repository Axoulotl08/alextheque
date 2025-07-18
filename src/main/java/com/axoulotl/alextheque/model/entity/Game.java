package com.axoulotl.alextheque.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @Column(name = "game_name",
            nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Console console;

    @Column(
            name = "game_creation_date",
            insertable = false,
            updatable = false,
            nullable = false,
            columnDefinition = "DATETIME default CURRENT_TIMESTAMP"
    )
    private LocalDateTime creationDate;

    @Column(
            name = "game_update_date",
            insertable = false,
            columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime updateDate;

    @Column(name = "game_inbox",
    columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean inbox;

    @Column(name = "game_startDate")
    private LocalDate startDate;

    @Column(name = "game_endDate")
    private LocalDate endDate;

    @Column(name = "game_Time")
    private Long gameTime;

    @ManyToOne
    @Column(name = "game_status")
    private Status status;
}
