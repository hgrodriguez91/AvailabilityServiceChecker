import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { HistoryRecord } from './interface/history-record';
import { Province } from './interface/province';
import { CheckerService } from "./services/checker.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {

  title = 'ServiceChecker';

  value: Date = new Date();
  icons: any =
    {
      "ok": "pi pi-check-circle",
      "down": "pi pi-ban",
      "warn": "pi pi-exclamation-circle",
      "unknow": "pi pi-question-circle"
    };

  buttonClass: any = {
    "ok": " p-button-success",
    "down": "p-button-danger",
    "warn": "p-button-warning",
    "unknow": "p-button-secondary"
  };

  lowAvProvince:Province = {};

  province: string = "";

  search: boolean = false;

  provinces: Array<string> = [];

  services: Array<string> = [];

  loadingData: boolean = true;

  allData: Array<any> = [];

  data: Array<any> = [];

  servicesStatus: HistoryRecord[] = [];

  hasErrors: boolean = false;

  Subscribe: Subscription;

  lastRecordUpdate: string = "0000:00:00 00:00:00";

  constructor(private service: CheckerService) {
    this.Subscribe = interval(300000).subscribe((func => {
      service.getLastStatus().subscribe((res: HistoryRecord[]) => {
        this.servicesStatus = [];
        this.servicesStatus = res;
        if (this.servicesStatus.length > 0) {
          this.prepareData();
        }
        this.getLastUpdateDate();
        this.getLowAvProvince();
        this.loadingData = false;
      });
    }), err => {
      if (err instanceof HttpErrorResponse && err.status == 0) {
        console.log('Error');
      }
    });
  }

  ngOnInit() {
    this.service.getLastStatus().subscribe((res: HistoryRecord[]) => {
      this.servicesStatus = [];
      this.servicesStatus = res;
      if (this.servicesStatus.length > 0) {
        this.prepareData();
      }
      this.getLastUpdateDate();
      this.getLowAvProvince();
      this.loadingData = false;
      console.log("Datos del l backend", res)
    }, err => {
      this.loadingData = false;
      this.hasErrors = true;
    });
  }

  getLastUpdateDate() {
    this.service.getLastDateChecked().subscribe(res => {

      this.lastRecordUpdate = res.toString().replace(/,/g, ':');
      this.search = false;
    }, err => {
      this.hasErrors = true;
      console.log("Error en la peticion :", err)
    })
  }

  loadSearch() {
    this.search = true;
    this.loadingData = true;      
    this.service.getRecordByDate(this.value).subscribe(res => {          
      this.servicesStatus=[];
      this.servicesStatus = res;
      if (this.servicesStatus.length > 0) {
        this.prepareData();
      }
      this.getLastUpdateDate();
      this.loadingData = false;
      this.search = false;
    }, err => {
      this.data=[];
      this.search = false;
      this.hasErrors = true;
      this.loadingData = false;
    })
  }
  
  getLowAvProvince(){
    this.service.getLowAvailabilityProvince().subscribe((res:Province)=>{
      this.lowAvProvince = res;
    })
  }

reload(){
  document.location.reload();
}
  prepareData() {
    this.allData =[];
    this.services = [];
    this.provinces = [];
    for (var key2 in this.servicesStatus[1].status) {
      this.services.push(key2);
    }
    this.servicesStatus.forEach(record => {
      this.provinces.push(record.province);
      record.status["Province"] = record.province;
      this.allData.push(record.status);
    });

    this.services.unshift("Province")
    this.data = this.allData;

  }

  filterProvince() {
    console.log(this.province);
    if (this.province != null) {
      this.data = this.allData.filter(dtos => { return dtos["Province"] == this.province })
    } else {
      this.data = this.allData
    }

  }

}
