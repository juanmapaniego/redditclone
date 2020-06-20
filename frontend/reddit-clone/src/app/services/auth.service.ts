import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { RegisterRequest } from "../commons/register-request";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { LoginRequest } from "../commons/login-request";
import { LoginResponse } from "../commons/login-response";
import { LocalStorageService } from "ngx-webstorage";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  refreshToken() {
    const refresTokenRequest = {
      refreshToken: this.getRefreshToken(),
      username: this.getUsername(),
    };

    return this.httpClient
      .post<LoginResponse>(
        "http://localhost:8080/api/auth/refresh/token",
        refresTokenRequest
      )
      .pipe(
        tap((res) => {
          this.localStorage.store(
            "authenticationToken",
            res.authenticationToken
          );
          this.localStorage.store("expiresAt", res.expiresAt);
        })
      );
  }
  getRefreshToken() {
    return this.localStorage.retrieve("refreshToken");
  }
  getUsername() {
    return this.localStorage.retrieve("username");
  }
  getJwtToken() {
    return this.localStorage.retrieve("authenticationToken");
  }

  constructor(
    private httpClient: HttpClient,
    private localStorage: LocalStorageService
  ) {}

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient
      .post<LoginResponse>("http://localhost:8080/api/auth/login", loginRequest)
      .pipe(
        map((data) => {
          this.localStorage.store(
            "authenticationToken",
            data.authenticationToken
          );
          this.localStorage.store("refreshToken", data.refreshToken);
          this.localStorage.store("username", data.username);
          this.localStorage.store("expiresAt", data.expiresAt);
          return true;
        })
      );
  }

  signup(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post(
      "http://localhost:8080/api/auth/signup",
      registerRequest,
      {
        responseType : "text"
      }
    );
  }
}
