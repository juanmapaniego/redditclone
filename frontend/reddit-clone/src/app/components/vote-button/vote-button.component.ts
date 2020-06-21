import { Component, OnInit, Input } from "@angular/core";
import { Post } from "src/app/commons/post";
import { faArrowUp, faArrowDown } from "@fortawesome/free-solid-svg-icons";
import { VoteService } from "src/app/services/vote.service";
import { Vote } from "src/app/commons/vote";
import { ToastrService } from "ngx-toastr";
import { PostService } from "src/app/services/post.service";
import { AuthService } from "src/app/services/auth.service";
import { VoteType } from 'src/app/commons/vote-type';
import { throwError } from 'rxjs';

@Component({
  selector: "app-vote-button",
  templateUrl: "./vote-button.component.html",
  styleUrls: ["./vote-button.component.css"],
})
export class VoteButtonComponent implements OnInit {
  @Input() post: Post;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  voteModel: Vote;
  upVoteColor: string;
  downVoteColor: string;

  constructor(
    private voteService: VoteService,
    private toastrService: ToastrService,
    private postService: PostService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.voteModel = new Vote();
  }

  downVote() {
    this.voteModel.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  upVote() {
    this.voteModel.voteType = VoteType.UPVOTE;
    this.vote();
  }

  vote() {
    this.voteModel.postId = this.post.postId;
    this.voteService.vote(this.voteModel).subscribe(
      () => {
        this.updateVoteDetails();
      },error => {
        this.toastrService.error(error.error.message);
        throwError(error);
      }
    );  
  }
  updateVoteDetails() {
    this.postService.getPost(this.post.postId).subscribe(
      data => this.post = data
    );
  }
}
