import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { authentication } from 'src/app/data/authentication-data';
import { User } from 'src/app/data/user-data';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public readonly API_KEY = 'authentication'
  private dynamicSubject = new BehaviorSubject<boolean>(false);
  public isloggedIn = this.dynamicSubject.asObservable();
  
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
    this.dynamicSubject.next(true);
  }

  getSession():string | null{
    return localStorage.getItem(this.API_KEY);
  }

  logout():void{
    localStorage.removeItem(this.API_KEY);
    this.dynamicSubject.next(false);
  }
}
