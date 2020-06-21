import { Injectable } from '@angular/core'; 
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { SubredditResponse } from '../commons/subreddit-response';

@Injectable({
  providedIn: 'root'
})
export class SubredditsService {
  createSubreddit(subredditModel: SubredditResponse) :Observable<SubredditResponse>{
    return this.httpClient.post<SubredditResponse>(
      "/api/subreddit",
      subredditModel
    );
  }

  constructor(private httpClient : HttpClient) { }

  getAllSubreddits(): Observable<Array<SubredditResponse>>{
    return this.httpClient.get<Array<SubredditResponse>>(
      "/api/subreddit"
    );
  }
}
