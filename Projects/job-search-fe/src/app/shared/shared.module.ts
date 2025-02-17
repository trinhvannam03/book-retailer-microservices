import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavbarComponent} from './navbar/navbar.component';
import {SearchSectionComponent} from './search-section/search-section.component';
import {FormsModule} from "@angular/forms";
import {provideHttpClient} from '@angular/common/http';
import {MatIcon} from '@angular/material/icon';
import {RouterLink} from '@angular/router';


@NgModule({
  declarations: [
    NavbarComponent,
    SearchSectionComponent],
  exports: [
    NavbarComponent,
    SearchSectionComponent
  ],
  providers: [provideHttpClient()],
  imports: [
    CommonModule,
    FormsModule,
    MatIcon,
    RouterLink
  ]
})
export class SharedModule {
}
