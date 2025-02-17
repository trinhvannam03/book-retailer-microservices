import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResultComponent } from './result.component';
import {SharedModule} from '../shared/shared.module';
import {RouterLink} from "@angular/router";
import {MatIconModule} from '@angular/material/icon';



@NgModule({
  declarations: [
    ResultComponent,

  ],
    imports: [
        CommonModule,
        SharedModule,
      MatIconModule,
      RouterLink
    ]
})
export class ResultModule { }
