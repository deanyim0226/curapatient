import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from 'src/app/data/employee-data';
import { EmployeeService } from 'src/app/services/employee/employee.service';

@Component({
  selector: 'app-employee-page',
  templateUrl: './employee-page.component.html',
  styleUrls: ['./employee-page.component.css']
})
export class EmployeePageComponent implements OnInit{

  employees?:Employee[];


  constructor(private employeeService: EmployeeService){

  }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (data) =>{
        this.employees = data;
      },
      error: (err) => {
        alert("error while retrieving the employees");
      }
    });
    
  }
}
