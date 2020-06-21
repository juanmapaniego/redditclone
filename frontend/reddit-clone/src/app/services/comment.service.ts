import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Comment } from "src/app/commons/comment";

@Injectable({
  providedIn: "root",
})
export class CommentService {
  getAllCommentsByUser(name: string) : Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(
      "/api/comments/by-user/" + name
    );
  }

  postComment(comment: Comment): Observable<any> {
    return this.httpClient.post(
      "/api/comments/", comment
    );
  }
  constructor(private httpClient: HttpClient) {}

  getCommentsByPost(id: number): Observable<Array<Comment>> {
    return this.httpClient.get<Array<Comment>>(
      "/api/comments/by-post/" + id
    );
  }
}
