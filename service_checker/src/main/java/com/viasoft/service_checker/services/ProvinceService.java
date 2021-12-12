package com.viasoft.service_checker.services;

import com.viasoft.service_checker.entities.Province;

import java.math.BigInteger;
import java.util.List;

public interface ProvinceService{

    Province getStateByName(String name);
    List<Province> getAllProvinces();
    Province getProvinceById(Long id);
}
