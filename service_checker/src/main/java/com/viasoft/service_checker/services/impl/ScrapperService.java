package com.viasoft.service_checker.services.impl;

import com.viasoft.service_checker.entities.HistoryRecord;
import com.viasoft.service_checker.entities.Province;
import com.viasoft.service_checker.repositories.HistoryRecordRepository;
import com.viasoft.service_checker.repositories.ProvinceRepository;
import com.viasoft.service_checker.repositories.ServiceRepository;
import com.viasoft.service_checker.services.Scrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service("scrappinService")
public class ScrapperService implements Scrapper {

    private final static Log LOG = LogFactory.getLog(ScrapperService.class);
    @Autowired
    HistoryRecordRepository historyRecordRepository;
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    ServiceRepository serviceRepository;

    @Override
    @Scheduled(cron = "0 */5 * * * *")
    public void loadServiceStatus() throws IOException {

       try {
           Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
           Element table = doc.select(".tabelaListagemDados").first();
           Elements headers = table.select("th");
           ArrayList<com.viasoft.service_checker.entities.Service> servicesList = serviceRepository.findAll();
           ArrayList<Province> provinceList = provinceRepository.findAll();
           LocalDateTime verification_date = LocalDateTime.now();

           LOG.info("Dentro del Scheduled JOb verificationdate :" + verification_date);
           for (int i = 1; i < headers.size(); i++) {
               String serviceName = headers.get(i).text();
               if (serviceRepository.findServiceByServiceName(serviceName) == null) {
                   com.viasoft.service_checker.entities.Service service = new com.viasoft.service_checker.entities.Service();
                   service.setServiceName(serviceName);
                   com.viasoft.service_checker.entities.Service saveService = serviceRepository.save(service);
                   servicesList.add(saveService);
               }
           }

           Elements bodyTable = table.select("tr");
           LOG.info("TR seleccionadas ");
           for (int i = 1; i < bodyTable.size(); i++) {
               Elements row = bodyTable.get(i).select("td");
                Province savedProvince = new Province();
               for (int j = 0; j < row.size(); j++) {
                   if (j == 0) {
                       LOG.info("Guardando Provincias en BD");
                       String provinceName = row.get(j).text();
                       Province existProvince = provinceRepository.findProvinceByName(provinceName);
                       if ( existProvince == null) {
                           Province province = new Province();
                           province.setName(provinceName);
                           savedProvince = provinceRepository.save(province);
                           provinceList.add(savedProvince);
                       }else{
                           savedProvince = existProvince;
                       }
                   } else {
                       Element img = row.get(j).select("img").first();
                       String status = "";
                       if (img != null) {

                           String src = img.attr("src");
                           if (src.contains("verde")) {
                               status = "ok";
                           }else if (src.contains("amarela")) {
                               status = "warn";
                           }else if (src.contains("vermelho")) {
                               status = "down";
                           }
                       } else {
                           status = "unknow";
                       }
                       LOG.info("Estado : " + status);
                       HistoryRecord historyRecord = new HistoryRecord();
                       historyRecord.setProvince(savedProvince);
                       historyRecord.setService(servicesList.get(j - 1));
                       historyRecord.setVerificationDate(verification_date);
                       historyRecord.setService_status(status);
                       historyRecordRepository.save(historyRecord);
                   }
               }

           }
       }catch (IOException e){
           LOG.error(e.getMessage());
           throw e;
       }
    }
}
