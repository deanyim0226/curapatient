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

  newEmployee:Employee = {
    employee_Id:0,
    name:'', 
    phone_number:'',
    supervisors:''
  }

  searchTerm:string = '';

  constructor(private employeeService: EmployeeService){

  }

  ngOnInit(): void {
    this.employeeService.employees.subscribe((data)=>{
      this.employees = data;
    })
  }
  
}
