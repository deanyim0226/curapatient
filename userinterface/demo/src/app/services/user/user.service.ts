import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { authentication } from 'src/app/data/authentication-data';
import { User } from 'src/app/data/user-data';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private loginSubject = new BehaviorSubject<boolean>(false);
  public loginStatus = this.loginSubject.asObservable();
  public readonly API_KEY = 'authentication'
  
  SpringBaseUrl:string = environment.SpringBaseUrl;;

  constructor(private http: HttpClient) { }

  registerUser(user:User):Observable<User>{
    return this.http.post<User>(this.SpringBaseUrl+ "/register", user);
  }

  login(user:User):Observable<authentication>{
    return this.http.post<authentication>(this.SpringBaseUrl+ "/login", user);
  }

  setApikey(apikey:string):void{
    localStorage.setItem(this.API_KEY,apikey);
    this.loginSubject.next(true);
  }

  getApiKey():string{
    const apikey = localStorage.getItem(this.API_KEY);

    if(apikey === null){
      return "";
    }
    
    return apikey;
  }

  logout():void{
    localStorage.removeItem(this.API_KEY);
    this.loginSubject.next(false);
  }
}
