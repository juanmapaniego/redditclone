import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { SubredditResponse } from 'src/app/commons/subreddit-response';
import { SubredditsService } from 'src/app/services/subreddits.service';

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {

  subreddits: Array<SubredditResponse> ;
  constructor(private subredditService: SubredditsService) { }

  ngOnInit() {
    this.subredditService.getAllSubreddits().subscribe(data => {
      this.subreddits = data;
    }, error => {
      throwError(error);
    })
  }
}