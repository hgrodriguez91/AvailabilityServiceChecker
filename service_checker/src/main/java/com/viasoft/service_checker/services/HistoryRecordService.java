package com.viasoft.service_checker.services;
import com.viasoft.service_checker.dtos.HistoryRecordDto;
import com.viasoft.service_checker.entities.Province;
import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRecordService {

    List<HistoryRecordDto> getLastHistoryRecord();

    HistoryRecordDto getAllHistoryRecordsByProvince(Province province);

    LocalDateTime getLasDateChecked();

    List<HistoryRecordDto> getHistoryRecordByGivenDate(LocalDateTime date);

    Province getProvinceWithWorstAvailability();
}
