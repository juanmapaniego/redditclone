import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { RegisterRequest } from "../commons/register-request";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { LoginRequest } from "../commons/login-request";
import { LoginResponse } from "../commons/login-response";
import { LocalStorageService } from "ngx-webstorage";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(
    private httpClient: HttpClient,
    private localStorage: LocalStorageService
  ) {}

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient
      .post<LoginResponse>("http://localhost:8080/api/auth/login", loginRequest)
      .pipe(
        map((data) => {
          this.localStorage.store("authenticationToken",data.authenticationToken);
          return true;
        })
      );
  }

  signup(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post(
      "http://localhost:8080/api/auth/signup",
      registerRequest
    );
  }
}
