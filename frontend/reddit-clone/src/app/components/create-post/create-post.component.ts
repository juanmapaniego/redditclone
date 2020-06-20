import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { PostService } from "src/app/services/post.service";
import { SubredditResponse } from "src/app/commons/subreddit-response";
import { SubredditsService } from "src/app/services/subreddits.service";
import { PostRequest } from 'src/app/commons/post-request';
import { throwError } from 'rxjs';

@Component({
  selector: "app-create-post",
  templateUrl: "./create-post.component.html",
  styleUrls: ["./create-post.component.css"],
})
export class CreatePostComponent implements OnInit {
  createPostForm: FormGroup;
  postRequest : PostRequest;
  subreddits : Array<SubredditResponse>;

  constructor(
    private router: Router,
    private postService: PostService,
    private subredditService: SubredditsService
  ) {
    this.createPostForm = new FormGroup({
      postName: new FormControl("", Validators.required),
      url: new FormControl("", Validators.required),
      subredditName: new FormControl("", Validators.required),
      description: new FormControl("", Validators.required),
    });
    this.postRequest = new PostRequest();
  }

  ngOnInit(): void {
    this.subredditService.getAllSubreddits().subscribe(
      data => {
        this.subreddits = data;
      },error =>{
        throwError(error);
      }      
    );
  }

  discardPost() {
    this.createPostForm.reset();
    this.router.navigateByUrl("/");
  }

  createPost() {
    this.postRequest.postName = this.createPostForm.get("postName").value
    this.postRequest.description = this.createPostForm.get("description").value
    this.postRequest.url = this.createPostForm.get("url").value
    this.postRequest.subredditName = this.createPostForm.get("subredditName").value

    this.postService.createPost(this.postRequest).subscribe(
      data =>{
        this.router.navigateByUrl("/");
      },error => {
        throwError(error);
      }
    );
  }
}
