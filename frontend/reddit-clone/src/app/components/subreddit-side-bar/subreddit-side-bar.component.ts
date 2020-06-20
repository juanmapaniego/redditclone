import { Component, OnInit } from '@angular/core';
import { SubredditsService } from 'src/app/services/subreddits.service';
import { SubredditResponse } from 'src/app/commons/subreddit-response';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {

  subreddits: Array<SubredditResponse> = [];
  dispayViewAll: boolean;
  
  constructor(private subredditService : SubredditsService) {
    subredditService.getAllSubreddits().subscribe(
      data => {
        if (data.length >=4 ){
          this.subreddits = data.splice(0,3);
          this.dispayViewAll = true;
        }else{
          this.subreddits = data;
          this.dispayViewAll = false;
        }
      }
    );
  }

  ngOnInit(): void {
  }

}
