import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Post } from '../commons/post';
import { PostRequest } from '../commons/post-request';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  
  getAllPostsByUser(name: string) : Observable<Array<Post>> {
    return this.httpClient.get<Array<Post>>(
      "http://localhost:8080/api/posts/by-user/" + name
    );
  }
  
  getPost(postId: number) : Observable<Post>{
    return this.httpClient.get<Post>(
      "http://localhost:8080/api/posts/"+postId
    );
  }

  createPost(postRequest: PostRequest) : Observable<any> {
    return this.httpClient.post(
      "http://localhost:8080/api/posts/",
      postRequest
    );
  }

  constructor(private httpClient : HttpClient) { }

  getAllSubreddits(): Observable<Array<Post>>{
    return this.httpClient.get<Array<Post>>(
      "http://localhost:8080/api/posts/"
    );
  }
}
