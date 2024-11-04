import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/data/user-data';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  
  loginObj:any = {
    username: '',
    password: ''
  }
  errorMessage:string | null = null;

  constructor(private userService: UserService, private router: Router){
  }

  login():void{

    this.userService.login(this.loginObj).subscribe({
      next: (data) =>{ 
       
        this.userService.setApikey(data.apikey);
        this.errorMessage = null;
        this.router.navigateByUrl('/employee');
      },
      error: (error) =>{
        alert("error while login" );
        this.errorMessage= "Password or Username is incorrect"

      }
    })
  }

  
}
