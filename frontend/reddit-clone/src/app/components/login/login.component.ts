import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginRequest } from 'src/app/commons/login-request';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : FormGroup;
  loginRequest : LoginRequest;

  constructor(private authService: AuthService) {
    this.loginRequest = new LoginRequest();
  }

  ngOnInit() {
    this.loginForm=new FormGroup({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),    
    });
  }

  login(){
    this.loginRequest.username = this.loginForm.get("username").value;
    this.loginRequest.password = this.loginForm.get("password").value;
    this.authService.login(this.loginRequest).subscribe(
      data => console.log("Login succesful")
    );
  }

}
