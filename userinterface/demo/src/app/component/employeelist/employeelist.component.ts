import { Component } from '@angular/core';
import { Employee } from 'src/app/data/employee-data';
import { EmployeeService } from 'src/app/services/employee/employee.service';

@Component({
  selector: 'app-employeelist',
  templateUrl: './employeelist.component.html',
  styleUrls: ['./employeelist.component.css']
})
export class EmployeelistComponent {

  employees?:Employee[];

  constructor(private employeeService: EmployeeService){

  }

  ngOnInit(): void {
    this.employeeService.employees.subscribe((employeeList)=>{
      this.employees = employeeList;
    })
  }


}
