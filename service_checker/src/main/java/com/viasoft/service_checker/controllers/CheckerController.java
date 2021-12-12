package com.viasoft.service_checker.controllers;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.viasoft.service_checker.dtos.HistoryRecordDto;
import com.viasoft.service_checker.entities.HistoryRecord;
import com.viasoft.service_checker.entities.Province;
import com.viasoft.service_checker.services.HistoryRecordService;
import com.viasoft.service_checker.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/checker")
public class CheckerController {

    @Autowired
    @Qualifier("history_service")
    private HistoryRecordService historyRecordService;

    @Autowired
    @Qualifier("province_service")
    private ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<List<HistoryRecordDto>>  getLastStatus() {
        try{
            List<HistoryRecordDto> list = historyRecordService.getLastHistoryRecord();

            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch ( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/province/{state}")
    public ResponseEntity<HistoryRecordDto> getStatusByProvice(@PathVariable String state){
        Province province = provinceService.getStateByName(state);
       HistoryRecordDto list = historyRecordService.getAllHistoryRecordsByProvince(province);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<HistoryRecordDto>> getStatusbyDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date ){
        List<HistoryRecordDto> list = historyRecordService.getHistoryRecordByGivenDate(date);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/lastdate")
    public ResponseEntity<LocalDateTime> getLastRecordUpdate(){
        return new ResponseEntity<>(historyRecordService.getLasDateChecked(),HttpStatus.OK);
    }

    @GetMapping("/province/availability")
    public ResponseEntity<Province> getProvinceWhitLowAvailability(){
        return new ResponseEntity<>(historyRecordService.getProvinceWithWorstAvailability(),HttpStatus.OK);
    }
}

