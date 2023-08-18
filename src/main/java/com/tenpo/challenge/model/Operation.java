package com.tenpo.challenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number1", nullable = false)
    private Double number1;
    @Column(name = "number2", nullable = false)
    private Double number2;
    private Double percentage;
    @Column(name = "sum", nullable = false)
    private Double sum;
    @Column(name = "date_operation", nullable = false)
    private LocalDateTime dateOperation;

    public Operation withNumber1(Double number1){
        this.number1 = number1;
        return this;
    }

    public Operation withNumber2(Double number2){
        this.number2 = number2;
        return this;
    }

    public Operation withPercentage(Double percentage){
        this.percentage = percentage;
        return this;
    }

    public Operation withDate(LocalDateTime dateOperation){
        this.dateOperation = dateOperation;
        return this;
    }

    public Operation calculateOperation(){
        this.sum = (this.number1 + this.number2) * ( 1 + this.percentage / 100);
        return this;
    }

}
