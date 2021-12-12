import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {PrimeModuleModule} from './prime-module/prime-module.module';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import {CheckerService} from './services/checker.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    PrimeModuleModule,
    BrowserAnimationsModule,
    HttpClientModule
    
  ],
  providers: [CheckerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
