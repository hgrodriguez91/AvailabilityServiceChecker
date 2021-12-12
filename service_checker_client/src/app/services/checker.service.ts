import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { HistoryRecord } from '../interface/history-record';



@Injectable({
  providedIn: 'root'
})
export class CheckerService {
 

  constructor(private http:HttpClient) { 
  
  }

  base_url="http://localhost:8080/checker"

  getLastStatus(){
    return this.http.get<HistoryRecord[]>( this.base_url); 
  }

  getLastDateChecked(){
    return this.http.get(this.base_url+"/lastdate")
  }

  getRecordByDate(date:Date){      
   return this.http.get<HistoryRecord[]>(this.base_url+"/date/"+date.toISOString())
  }
getLowAvailabilityProvince(){
 return this.http.get(this.base_url+"/province/availability");
}

}
