package com.axoulotl.alextheque.model.entity;

import com.axoulotl.alextheque.model.entity.enums.Zone;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Getter
@Setter
@Builder
public class Console {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "console_id")
    private Integer id;

    @Column(name = "console_name",
            length = 100,
            nullable = false)
    private String name;

    @Column(name = "console_launch_date", nullable = false)
    private LocalDateTime launchDate;

    @Column(name = "console_manufacturer",
            length = 100,
            nullable = false )
    private String manufacturer;

    @Column(
            name = "console_creation_date",
            insertable = false,
            updatable = false,
            nullable = false,
            columnDefinition = "DATETIME default CURRENT_TIMESTAMP"
    )
    private LocalDateTime creationDate;

    @Column(name = "console_zone", nullable = false)
    @Enumerated(EnumType.STRING)
    private Zone zone;
}
