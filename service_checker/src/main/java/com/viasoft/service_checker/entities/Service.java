package com.viasoft.service_checker.entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "service")
@Data
public class Service {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name")
    @NotNull
    private String serviceName;
}
