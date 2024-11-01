import { Component } from '@angular/core';
import { User } from 'src/app/data/user-data';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  
  
  constructor(private userService: UserService){

  }

  login(user:User){

    this.userService.login(user).subscribe({
      next: (data) =>{
         this.userService.setSession(data.apiKey);
      },
      error: (error) =>{
        alert("error while login");
      }
    })
  }

  
}
