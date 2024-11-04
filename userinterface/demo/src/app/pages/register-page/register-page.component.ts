import { Component } from '@angular/core';
import { User } from 'src/app/data/user-data';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  registerObj:any = {
    username:'',
    password:'',
    re_password:''
  }
  errorMessage:string | null = null;
  


  constructor(private userService:UserService){

  }

  register(): void{

    if(this.registerObj.username.length < 2){
      alert("error check your name");
      this.errorMessage = "Username should be greater than 2";
      return;
    }

    if(this.registerObj.password !== this.registerObj.re_password){
      alert("check password again");
      this.errorMessage = "Password does not match";
      return ;
    }


      this.userService.registerUser(this.registerObj).subscribe({
        next: (data)=>{
          alert("successfully created an user " + data);
          this.errorMessage = null;
        },
        error: (err) =>{
          alert("error while creating an user");
          this.errorMessage = "Username is already taken by other users";
        }
      })
  }

}
