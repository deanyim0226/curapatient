import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  isloggedIn?:boolean;

  constructor(private service: UserService){

  }
  ngOnInit(): void {
    
    this.service.loginStatus.subscribe(result=>{
      this.isloggedIn = result;
      
      if(localStorage.getItem(this.service.API_KEY) !== undefined && localStorage.getItem(this.service.API_KEY) !== null){
        this.isloggedIn = true;
      }
    })
  }

  logout(){
    this.service.logout();
  }
}
