package com.viasoft.service_checker.repositories;

import com.viasoft.service_checker.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Serializable> {

    Province findProvinceByName(String name);
    ArrayList<Province>findAll();
    Province findProvinceById(Long id);
}
