import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginRequest } from 'src/app/commons/login-request';
import { AuthService } from 'src/app/services/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { isError } from 'util';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : FormGroup;
  loginRequest : LoginRequest;
  registerSuccesMessage: string;
  isError: boolean;

  constructor(private authService: AuthService,
    private activatedRoute : ActivatedRoute,
    private router : Router,
    private toastr : ToastrService) {
    this.loginRequest = new LoginRequest();
  }

  ngOnInit() {
    this.loginForm=new FormGroup({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),    
    });

    this.activatedRoute.queryParams.subscribe(params => {
      if(params.registered !== undefined && params.registered === "true"){
        this.toastr.success("Signup Succesful");
        this.registerSuccesMessage = "Please check your inbox for activation email "
          + "activate your account before you Login!";  
      }
    });
  }

  login(){
    this.loginRequest.username = this.loginForm.get("username").value;
    this.loginRequest.password = this.loginForm.get("password").value;
    this.authService.login(this.loginRequest).subscribe(
      data => {
        if(data){
          this.isError = false;
          this.router.navigateByUrl("/");
          this.toastr.success("Login Succesful");
        }else{
          this.isError = true;
        }
      }
    );
  }

}
