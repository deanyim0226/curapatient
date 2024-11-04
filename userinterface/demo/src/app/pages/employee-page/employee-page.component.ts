import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Employee } from 'src/app/data/employee-data';
import { EmployeeService } from 'src/app/services/employee/employee.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-employee-page',
  templateUrl: './employee-page.component.html',
  styleUrls: ['./employee-page.component.css']
})
export class EmployeePageComponent implements OnInit{
  
  constructor(){

  }

  ngOnInit(): void {

  }

}
