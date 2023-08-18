package com.tenpo.challenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table
public class Shedlock {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime lockUntil;
    private LocalDateTime lockedAt;
    private String lockedBy;

    public Shedlock() {
    }

    public Shedlock(String name, LocalDateTime lockUntil, LocalDateTime lockedAt, String lockedBy) {
        this.name = name;
        this.lockUntil = lockUntil;
        this.lockedAt = lockedAt;
        this.lockedBy = lockedBy;
    }
}
