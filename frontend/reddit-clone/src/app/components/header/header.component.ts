import { Component, OnInit } from '@angular/core';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { RefreshToken } from 'src/app/commons/refresh-token';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  faUser = faUser;
  isLoggedIn : boolean;
  username : string;
  token : RefreshToken;

  constructor(private router : Router, private authService : AuthService) { }

  ngOnInit() {
    this.authService.loggedIn.subscribe((data:boolean) => this.isLoggedIn = data);
    this.authService.username.subscribe((data:string) => this.username = data);
    this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUsername();
    this.token = new RefreshToken();
  }

  logout(){
    this.token.refreshToken = this.authService.getRefreshToken();
    this.token.username = this.authService.getUsername();
    this.authService.logout(this.token);
  }

  goToUserProfile() {
    this.router.navigateByUrl("/user-profile/" + this.username);
  }

}
