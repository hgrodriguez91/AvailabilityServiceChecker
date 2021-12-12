package com.viasoft.service_checker.services.impl;

import com.viasoft.service_checker.entities.Province;
import com.viasoft.service_checker.repositories.ProvinceRepository;
import com.viasoft.service_checker.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("province_service")
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    public Province getStateByName(String name) {
        return provinceRepository.findProvinceByName(name);
    }

    @Override
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getProvinceById(Long id) {
        return provinceRepository.findProvinceById(id);
    }

}
