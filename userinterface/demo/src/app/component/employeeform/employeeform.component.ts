import { Component } from '@angular/core';
import { Employee } from 'src/app/data/employee-data';
import { EmployeeService } from 'src/app/services/employee/employee.service';


@Component({
  selector: 'app-employeeform',
  templateUrl: './employeeform.component.html',
  styleUrls: ['./employeeform.component.css']
})
export class EmployeeformComponent {
 
  employees?:Employee[];

  employee:Employee = {
    employee_id:0,
    name:'', 
    phone_number:'',
    supervisors:''
  }

  searchTerm:string = '';

  updateFlag:boolean = false;

  constructor(private employeeService: EmployeeService){

  }

  ngOnInit(): void {
    this.employeeService.employees.subscribe((latestEmployees)=>{
      this.employees = latestEmployees;
    })
  }
  
  resetEmployee():void{
    this.employee = {
      employee_id:0,
      name:'', 
      phone_number:'',
      supervisors:''
    }
  } 

  addEmployee():void{
    this.employeeService.saveEmployee(this.employee).subscribe({
      next: (data)=>{
        alert("employee is succesfully added");
      },
      error: err=>{
        alert("error while adding employee")
      }
    });
  }

  updateEmployee():void{
    this.employeeService.updateEmployee(this.employee).subscribe({
      next: (data)=>{
        alert("employee is succesfully updated");
      },
      error: err=>{
        alert("error while updating employee")
      }
    })
    
    this.updateFlag = false;
  }

  deleteEmployee(id:number){
    alert("delete")
    this.employeeService.deleteEmployeeById(id).subscribe({
      next: (data)=>{
        alert("employee is succesfully deleted");
      },
      error: err=>{
        alert("error while deleting employee")
      }
    });

  }

  sendUpdate(id:number):void{
    
    this.employeeService.getEmployeeById(id).subscribe({
      next: (retrievedEmployee) =>{
        this.employee = retrievedEmployee;
      },
      error: (err)=>{
        alert("error while finding employee by id " + id);
      }
    })
    this.updateFlag = true;

  }
 
  get filteredEmployees() {
    
    if(this.employees){
      return this.employees.filter(currEmployee =>
        currEmployee.employee_id.toString().includes(this.searchTerm) ||
        currEmployee.name.toLowerCase().includes(this.searchTerm.toLowerCase()) 
      );
    }

    return;
  }

}
