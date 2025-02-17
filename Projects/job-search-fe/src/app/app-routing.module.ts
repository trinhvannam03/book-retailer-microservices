import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ResultComponent} from './result/result.component';

const appRoutes: Routes = [
  {path: "", pathMatch: 'full', component: HomeComponent},
  {path: "search", pathMatch: 'prefix', component: ResultComponent},
  {
    path: "auth", pathMatch: 'prefix', loadChildren: () => (import('./auth/auth.module')
      .then(authModule => authModule.AuthModule))
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
