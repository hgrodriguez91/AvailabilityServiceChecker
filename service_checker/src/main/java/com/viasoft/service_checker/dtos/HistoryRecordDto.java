package com.viasoft.service_checker.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HistoryRecordDto {

    private String province;
    private HashMap<String,String>status;

}
