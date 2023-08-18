package com.tenpo.challenge.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "percentage")
@Getter
@Setter
public class Percentage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double percentage;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    public Percentage(){};

    public Percentage(Integer id, Double percentage, LocalDateTime startDate) {
        this.id = id;
        this.percentage = percentage;
        this.startDate = startDate;
    }


}
