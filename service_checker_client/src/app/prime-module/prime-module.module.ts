import { NgModule } from '@angular/core';
import {CommonModule } from '@angular/common';
import {CalendarModule} from 'primeng/calendar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import { ChipModule } from 'primeng/chip';
import {TooltipModule} from 'primeng/tooltip';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CalendarModule,
    TableModule,
    DropdownModule,
    ProgressSpinnerModule,
    ChipModule,
    TooltipModule
  ],
  exports:[
    CalendarModule,
    TableModule,
    DropdownModule,
    ProgressSpinnerModule,
    ChipModule,
    TooltipModule
  ]
})
export class PrimeModuleModule { }
