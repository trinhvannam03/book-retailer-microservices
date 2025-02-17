import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {HomeComponent} from './home.component';
import {NavbarComponent} from '../shared/navbar/navbar.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [
    HomeComponent],
  imports: [
    CommonModule,
    NgOptimizedImage,
    SharedModule
  ],
  exports: [
    HomeComponent
  ]
})
export class HomeModule {
}
