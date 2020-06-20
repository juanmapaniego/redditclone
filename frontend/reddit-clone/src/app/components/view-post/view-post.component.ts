import { Component, OnInit } from "@angular/core";
import { Post } from "src/app/commons/post";
import { PostService } from "src/app/services/post.service";
import { ActivatedRoute } from "@angular/router";
import { throwError, from } from 'rxjs';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommentService } from 'src/app/services/comment.service';
import { Comment } from "src/app/commons/comment";

@Component({
  selector: "app-view-post",
  templateUrl: "./view-post.component.html",
  styleUrls: ["./view-post.component.css"],
})
export class ViewPostComponent implements OnInit {
  postId: number;
  post: Post = new Post();
  commentForm : FormGroup;
  commentModel : Comment;
  comments : Array<Comment>;

  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private commentService : CommentService
  ) {
    this.postId = activatedRoute.snapshot.params.id;
    this.commentForm = new FormGroup({
      text : new FormControl("", Validators.required)
    });
    this.commentModel = new Comment();        
  }

  ngOnInit(): void {
    this.getPost();
    this.getCommentsById(); 
  }

  getCommentsById(){
    this.commentService.getCommentsByPost(this.postId).subscribe(
      data => {
        this.comments = data;
      },error => {
        throwError(error);
      }
    )
  }

  postComment(){
    this.commentModel.text = this.commentForm.get("text").value;
    this.commentModel.postId = this.postId;
    this.commentService.postComment(this.commentModel).subscribe(
      data => {
        this.commentForm.reset();
        this.getCommentsById();
      },error => {
        throwError(error);
      }
    );
  }

  getPost(){
    this.postService.getPost(this.postId).subscribe(
      data => {
        this.post = data;
        if(this.post.description === null){
          this.post.description = "Vacio";
        }        
      },error => {
        throwError(error);
      }
    );
  }
}
