package com.viasoft.service_checker.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryRecord {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "service_status")
    private  String service_status;

    @Column(name = "verification_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verificationDate;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Province province;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Service service;
}
