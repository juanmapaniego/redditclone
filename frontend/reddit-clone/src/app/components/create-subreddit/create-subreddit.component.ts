import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SubredditResponse } from 'src/app/commons/subreddit-response';
import { Router } from '@angular/router';
import { SubredditsService } from 'src/app/services/subreddits.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {

  createSubredditForm : FormGroup;
  subredditModel : SubredditResponse;
  
  constructor(private router : Router,
    private subredditService : SubredditsService) { 
    this.createSubredditForm = new FormGroup({
      title  : new FormControl("", Validators.required),
      description : new FormControl("", Validators.required)
    });
    this.subredditModel = new SubredditResponse();
  }

  ngOnInit(): void {
  }

  discard(){
    this.createSubredditForm.reset();
    this.router.navigateByUrl("/");
  }

  createSubreddit(){
    this.subredditModel.name = this.createSubredditForm.get("title").value
    this.subredditModel.description = this.createSubredditForm.get("description").value
    this.subredditService.createSubreddit(this.subredditModel).subscribe(
      data => {
        this.router.navigateByUrl("/list-subreddits");
      },
      error => {
        throwError(error);
      }
    );
  }

}
