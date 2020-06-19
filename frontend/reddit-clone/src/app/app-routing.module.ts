import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';


const routes: Routes = [
  {path:"sign-up",component: SignupComponent},
  {path:"login",component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
