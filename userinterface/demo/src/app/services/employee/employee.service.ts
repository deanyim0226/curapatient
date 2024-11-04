import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

import {Employee} from "../../data/employee-data";
import { UserService } from '../user/user.service';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private dynamicSubject = new BehaviorSubject<Employee[]>([]);
  public employees = this.dynamicSubject.asObservable();

  SpringBaseUrl:string = 'http://localhost:8088';
  
  /*
    http request, subscribe method is needed to execute the request
    subscribe method taks next and error callbacks. 
    when it is successfuly then next, otherwise error 

    Httpclient returns Observable 

    Observable is a data source that allows you to work with asynchronous data
  */

  constructor(private http: HttpClient, private userService:UserService) { 

    this.getAllEmployees();
  }

  getEmployeeById(id:number): Observable<Employee> {

    let params =new HttpParams().set("id",id.toString());
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeById",  {params});
  }

  getEmployeeByName(name:string): Observable<Employee> {

    let params =new HttpParams().set("name",name);
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeByName",  {params});
  } 

  getAllEmployees():void{

     this.http.get<Employee[]>(this.SpringBaseUrl+ '/getAllemployees').subscribe({
          next: data =>{
            this.dynamicSubject.next(data);
          },
          error: error => {

          }          
       
        });
  }

  saveEmployee(employee:Employee): Observable<Employee>{
  
    return this.http.post<Employee>(this.SpringBaseUrl+"/saveEmployee", employee).pipe(
      tap((savedEmployee)=>{
        const currentEmployees = this.dynamicSubject.value;
        this.dynamicSubject.next([...currentEmployees, savedEmployee]);
      })
    )
  }
  
  updateEmployee(employee:Employee): Observable<Employee> {

    return this.http.put<Employee>(this.SpringBaseUrl + "/updateEmployee", employee).pipe(
      tap((updatedEmployee)=>{
        const  updatedEmployees = this.dynamicSubject.value.map((existedEmployee)=> existedEmployee.employee_Id === employee.employee_Id ? updatedEmployee : existedEmployee);
        this.dynamicSubject.next(updatedEmployees);
      })
    )
  }

  deleteEmployeeById(id:number): Observable<Employee> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.delete<Employee>(this.SpringBaseUrl+ "/deleteEmployeeById", {params}).pipe(
      tap(()=>{
        const updatedEmployees = this.dynamicSubject.value.filter((existedEmployee)=> existedEmployee.employee_Id !== id)

        this.dynamicSubject.next(updatedEmployees);
      })
    )
  }
}
