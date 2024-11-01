import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { authentication } from 'src/app/data/authentication-data';
import { User } from 'src/app/data/user-data';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private readonly API_KEY = 'authentication'
  SpringBaseUrl:string = 'http://localhost:8088';

  constructor(private http: HttpClient) { }

  registerUser(user:User):Observable<User>{
    return this.http.post<User>(this.SpringBaseUrl+ "/register", user);
  }

  login(user:User):Observable<authentication>{
    return this.http.post<authentication>(this.SpringBaseUrl+ "/login", user);
  }

  setSession(apikey:string):void{
    localStorage.setItem(this.API_KEY,apikey);
  }

  getSession():string | null{
    return localStorage.getItem(this.API_KEY);
  }

  logout():void{
    localStorage.removeItem(this.API_KEY);
  }
}
