package com.viasoft.service_checker.repositories;

import com.viasoft.service_checker.entities.HistoryRecord;
import com.viasoft.service_checker.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryRecordRepository extends JpaRepository<HistoryRecord, Serializable> {


    @Query("select new HistoryRecord(hr.id,hr.service_status,hr.verificationDate,hr.province,hr.service) from HistoryRecord hr where hr.verificationDate = :date")
    List<HistoryRecord> findLastCheckServiceStatus(LocalDateTime date);

    List<HistoryRecord>findAllByProvinceAndVerificationDate(Province province, LocalDateTime date);

    @Query(value ="Select hr.verification_date from History_Record hr order by hr.verification_date desc limit 1",nativeQuery=true)
    LocalDateTime findLastDateTimeChecked();

    @Query(value="select hr.verification_date from History_Record hr where hr.verification_date < :date order by hr.verification_date desc limit 1",nativeQuery=true)
    LocalDateTime findLastCheckedAfterDate(LocalDateTime date);

    @Query(value="Select hr.province_id from History_Record hr  where hr.service_status = 'down' or hr.service_status = 'unknow' group by hr.province_id order by COUNT(hr.service_status) desc limit 1",nativeQuery=true)
    Long worstServiceAvailavility();

}
