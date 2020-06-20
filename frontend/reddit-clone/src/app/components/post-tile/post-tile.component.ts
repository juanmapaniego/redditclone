import { Component, OnInit, Input } from '@angular/core';
import { Post } from 'src/app/commons/post';
import { faComments } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {
  
  @Input() data : Array<Post>
  faComments = faComments;

  constructor() { }

  ngOnInit(): void {
  }

}
