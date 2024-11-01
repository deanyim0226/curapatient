import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import {Employee} from "../../data/employee-data";


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  SpringBaseUrl:string = 'http://localhost:8088';

  constructor(private http: HttpClient) { }

  getEmployeeById(id:number): Observable<Employee> {

    let params =new HttpParams().set("id",id.toString());
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeById",  {params});
  }

  getEmployeeByName(name:string): Observable<Employee> {

    let params =new HttpParams().set("name",name);
    return this.http.get<Employee>(this.SpringBaseUrl+"/getEmployeeByName",  {params});
  } 

  getAllEmployees(): Observable<Employee[]>{

    return this.http.get<Employee[]>(this.SpringBaseUrl+ '/getAllemployees');
  }


  saveEmployee(employee:Employee): Observable<Employee>{

    return this.http.post<Employee>(this.SpringBaseUrl+"/saveEmployee", employee);
  }
  
  updateEmployee(employee:Employee): Observable<Employee> {

    return this.http.put<Employee>(this.SpringBaseUrl + "/updateEmployee", employee)
  }

  deleteEmployeeById(id:number): Observable<Employee> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.delete<Employee>(this.SpringBaseUrl+ "/deleteEmployeeById", {params});
  }

}
