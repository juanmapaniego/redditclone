import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxWebstorageModule } from "ngx-webstorage";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ToastrModule } from "ngx-toastr";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { EditorModule } from "@tinymce/tinymce-angular";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { SignupComponent } from './components/signup/signup.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { PostTileComponent } from './components/post-tile/post-tile.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { SubredditSideBarComponent } from './components/subreddit-side-bar/subreddit-side-bar.component';
import { VoteButtonComponent } from './components/vote-button/vote-button.component';
import { TokenInterceptor } from "./token-interceptor";
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CreateSubredditComponent } from './components/create-subreddit/create-subreddit.component';
import { ListSubredditsComponent } from './components/list-subreddits/list-subreddits.component';
import { ViewPostComponent } from './components/view-post/view-post.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    PostTileComponent,
    SideBarComponent,
    SubredditSideBarComponent,
    VoteButtonComponent,
    CreatePostComponent,
    CreateSubredditComponent,
    ListSubredditsComponent,
    ViewPostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
