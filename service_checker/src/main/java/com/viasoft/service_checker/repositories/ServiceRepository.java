package com.viasoft.service_checker.repositories;

import com.viasoft.service_checker.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Serializable> {

    Service findServiceByServiceName(String name);
    ArrayList<Service>findAll();
}
