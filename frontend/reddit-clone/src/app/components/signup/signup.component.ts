import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RegisterRequest } from 'src/app/commons/register-request';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  registerRequest: RegisterRequest;
  signupForm: FormGroup;

  constructor(private authService : AuthService,
    private router : Router,
    private toastr : ToastrService) {
    this.registerRequest = new RegisterRequest(); 
  }

  ngOnInit() {  
    this.signupForm = new FormGroup({
      username: new FormControl("",Validators.required),
      email: new FormControl("",[Validators.required, Validators.email]),
      password: new FormControl("",[Validators.required,Validators.minLength(6)]),
      confirmPassword: new FormControl("",Validators.required),
    });
  }

  signup(){
    this.registerRequest.email = this.signupForm.get("email").value;
    this.registerRequest.password = this.signupForm.get("password").value;
    this.registerRequest.repeatedPassword = this.signupForm.get("confirmPassword").value;
    this.registerRequest.username = this.signupForm.get("username").value;
    this.authService.signup(this.registerRequest).subscribe(
      () => {
        this.router.navigate(
          ["/login"],
          {
            queryParams:{  registered : "true" }
          }
        );
      },
      (error) => {
        console.log(error);
        this.toastr.error("Registration error! Please try again");
      }
    );
    
  }
  
  valid() : boolean{
    return this.signupForm.get("username").valid;
  }

}
