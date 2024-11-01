import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './data/employee-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html', //html file
  styleUrls: ['./app.component.css'] //style file
})
export class AppComponent {
  title = 'Appdemo';

  SpringBaseUrl:string = 'http://localhost:8088';

  employees:any
  employee?:Employee

  constructor(private http: HttpClient) { }

  saveEmployee(employee:Employee): any{

    this.http.post(this.SpringBaseUrl+"/saveEmployee", employee).subscribe({
      next: (data) =>{

      },
      error: (err) =>{

      }
    })
  }
  
  updateEmployee(employee:Employee): void {

    this.http.put(this.SpringBaseUrl + "/updateEmployee", employee).subscribe({
      next: (data) =>{

      },
      error(err) {
        alert("error"); 
      }
    })

  }

  deleteEmployeeById(id:Number): void {
    let params = new HttpParams().set("id", id.toString());

    this.http.delete(this.SpringBaseUrl+ "/deleteEmployeeById", {params}).subscribe({
      next: (data) =>{
        alert("success" + data);
      },
      error: (err) =>{
        alert("error")
      }

    })
  }

  getEmployeeById(id:Number): void {

    let params = new HttpParams().set("id",id.toString());
  
    this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeById",  {params}).subscribe({
      next: (data) =>{
        this.employee = data;
      },
      error: (err) => {
        alert("error")
      }
   })
  }

  getEmployeeByName(name:string): void {

    let params =new HttpParams().set("name",name);
  
    this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeByName",  {params}).subscribe({
      next: (data) =>{
        this.employee = data;
      },
      error: (err) => {
        alert("error");
      }
   })
  } 



  getAll(){

    /*
    http request, subscribe method is needed to execute the request
    subscribe method taks next and error callbacks. 
    when it is successfuly then next, otherwise error 

    Httpclient returns Observable 

    Observable is a data source that allows you to work with asynchronous data

    */

    this.http.get(this.SpringBaseUrl+ '/getAllemployees').subscribe({
        next: (data) =>{
          this.employees = data;
        },
        error:(err) =>{
          alert("error");
        }
      }

      
    );

  }
}
