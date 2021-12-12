package com.viasoft.service_checker.services.impl;

import com.viasoft.service_checker.dtos.HistoryRecordDto;
import com.viasoft.service_checker.entities.HistoryRecord;
import com.viasoft.service_checker.entities.Province;
import com.viasoft.service_checker.repositories.HistoryRecordRepository;
import com.viasoft.service_checker.services.HistoryRecordService;
import com.viasoft.service_checker.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("history_service")
public class HistoryRecordServiceImpl implements HistoryRecordService {

    @Autowired
    private HistoryRecordRepository historyRecordRepository;
    @Autowired
    @Qualifier("province_service")
    private ProvinceService provinceService;

    @Override
    public List<HistoryRecordDto> getLastHistoryRecord() {
        LocalDateTime date = historyRecordRepository.findLastDateTimeChecked();
        List<Province> allProvinces = provinceService.getAllProvinces();
        List<HistoryRecordDto> recordsDto = new ArrayList<HistoryRecordDto>();
        allProvinces.forEach(province -> {
            List<HistoryRecord> records = historyRecordRepository.findAllByProvinceAndVerificationDate(province,date);
            recordsDto.add(mapToDto(records));
        });
        return recordsDto;
            }

    @Override
    public  HistoryRecordDto getAllHistoryRecordsByProvince(Province province){
        LocalDateTime date = historyRecordRepository.findLastDateTimeChecked();
        List<HistoryRecord> records = historyRecordRepository.findAllByProvinceAndVerificationDate(province,date);
        return mapToDto(records);
    }

    @Override
    public LocalDateTime getLasDateChecked() {
        return historyRecordRepository.findLastDateTimeChecked();
    }



    @Override
    public List<HistoryRecordDto> getHistoryRecordByGivenDate(LocalDateTime date) {
        LocalDateTime lastDateAfter = historyRecordRepository.findLastCheckedAfterDate(date);
        List<Province> allProvinces = provinceService.getAllProvinces();
        List<HistoryRecordDto> recordsDto = new ArrayList<HistoryRecordDto>();
        allProvinces.forEach(province -> {
            List<HistoryRecord> records = historyRecordRepository.findAllByProvinceAndVerificationDate(province,lastDateAfter);
            recordsDto.add(mapToDto(records));
        });
        return recordsDto;

    }

    @Override
    public Province getProvinceWithWorstAvailability() {
        Long provinceId = historyRecordRepository.worstServiceAvailavility();
        return provinceService.getProvinceById(provinceId);
    }

    //Record by de same state
    public HistoryRecordDto mapToDto(List<HistoryRecord> records){
        HistoryRecordDto historyRecordDto = new HistoryRecordDto();
        historyRecordDto.setProvince(records.get(1).getProvince().getName());
        HashMap<String,String>status = new HashMap<String,String>();
        records.forEach(historyRecord -> {
            status.put(historyRecord.getService().getServiceName(),historyRecord.getService_status());
        });
        historyRecordDto.setStatus(status);
        return historyRecordDto;
    }
}
