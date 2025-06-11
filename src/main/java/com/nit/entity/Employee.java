package com.nit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EMP_TABLE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private Double salary;
}
