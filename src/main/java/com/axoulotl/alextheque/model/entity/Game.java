package com.axoulotl.alextheque.model.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "game_name")
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

    @Column(name = "game_inbox")
    private Boolean inbox;
}
