package com.viasoft.service_checker.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Province {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

}
