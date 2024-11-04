import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

import {Employee} from "../../data/employee-data";
import { UserService } from '../user/user.service';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private employeeSubject = new BehaviorSubject<Employee[]>([]);
  public employees = this.employeeSubject.asObservable();

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

  setHttpHeaders():HttpHeaders{
    let apikey = this.userService.getApiKey();
    return new HttpHeaders().set("x-api-key", apikey);
  }

  getEmployeeById(id:number): Observable<Employee> {
    const headers = this.setHttpHeaders();
    const params =new HttpParams().set("id",id.toString());
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeById",  {headers, params});
  }

  getEmployeeByName(name:string): Observable<Employee> {
    const headers = this.setHttpHeaders();
    let params =new HttpParams().set("name",name);
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeByName",  {headers, params});
  } 

  getAllEmployees():void{
    const headers = this.setHttpHeaders();
     this.http.get<Employee[]>(this.SpringBaseUrl+ '/getAllemployees', {headers}).subscribe({
          next: data =>{
            this.employeeSubject.next(data);
          },
          error: error => {
            alert("error while retrieving all employees")
          }          
        });
  }

  saveEmployee(employee:Employee): Observable<Employee>{
    const headers = this.setHttpHeaders();
    return this.http.post<Employee>(this.SpringBaseUrl+"/saveEmployee" , employee, {headers}).pipe(
      tap((savedEmployee)=>{
        const currentEmployees = this.employeeSubject.value;
        this.employeeSubject.next([...currentEmployees, savedEmployee]);
      })
    )
  }
  
  updateEmployee(employee:Employee): Observable<Employee> {
    const headers = this.setHttpHeaders();
    return this.http.put<Employee>(this.SpringBaseUrl + "/updateEmployee", employee, {headers}).pipe(
      tap((updatedEmployee)=>{
        const  updatedEmployees = this.employeeSubject.value.map((existedEmployee)=> existedEmployee.employee_Id === employee.employee_Id ? updatedEmployee : existedEmployee);
        this.employeeSubject.next(updatedEmployees);
      })
    )
  }

  deleteEmployeeById(id:number): Observable<Employee> {
    const headers = this.setHttpHeaders();
    let params = new HttpParams().set("id", id.toString());
    return this.http.delete<Employee>(this.SpringBaseUrl+ "/deleteEmployeeById", {params,headers}).pipe(
      tap(()=>{
        const updatedEmployees = this.employeeSubject.value.filter((existedEmployee)=> existedEmployee.employee_Id !== id)
        this.employeeSubject.next(updatedEmployees);
      })
    )
  }
}
