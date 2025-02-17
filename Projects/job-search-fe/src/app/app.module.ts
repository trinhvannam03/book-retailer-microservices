import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeModule} from './home/home.module';
import {AuthModule} from './auth/auth.module';
import {ResultModule} from './result/result.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    ResultModule,
    AuthModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
