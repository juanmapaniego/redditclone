export class RegisterRequest {
  email: string;
  password: string;
  repeatedPassword: string;
  username: string;

  constructor(){
      this.email="";
      this.password="";
      this.repeatedPassword="";
      this.username="";
  }
}
