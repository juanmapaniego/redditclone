import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { RegisterRequest } from '../commons/register-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  signup(registerRequest: RegisterRequest) : Observable<any>{
    return this.httpClient.post(
      "http://localhost:8080/api/auth/signup",
      registerRequest,
      {
        responseType: "text"
      }
    )
  }
}
