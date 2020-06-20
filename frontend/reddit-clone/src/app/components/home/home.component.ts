import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/commons/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts$ : Array<Post> = [];
  
  constructor(private postService : PostService) {
    postService.getAllSubreddits().subscribe(
      data => this.posts$ = data
    );
  }

  ngOnInit(): void {
  }

}
