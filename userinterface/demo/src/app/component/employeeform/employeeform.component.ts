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
    employee_Id:0,
    name:'', 
    phone_number:'',
    supervisors:''
  }

  searchTerm:string = '';

  updateFlag:boolean = false;

  constructor(private employeeService: EmployeeService){

  }

  ngOnInit(): void {
    this.employeeService.employees.subscribe((data)=>{
      this.employees = data;
    })
  }

  resetEmployee():void{
    this.employee = {
      employee_Id:0,
      name:'', 
      phone_number:'',
      supervisors:''
    }
  } 

  addEmployee():void{
    alert("add")


    this.employeeService.saveEmployee(this.employee).subscribe({
      next: (data)=>{
        console.log(data);
      },
      error: err=>{

      }
    });
  }

  updateEmployee():void{
    alert("update")
   
    this.employeeService.updateEmployee(this.employee).subscribe({
      next: (data)=>{
        console.log(data);
      },
      error: err=>{

      }
    })
    
    this.updateFlag = false;
  }

  deleteEmployee(id:number){
    alert("delete")
    this.employeeService.deleteEmployeeById(id).subscribe({
      next: (data)=>{
        console.log(data);
      },
      error: err=>{

      }
    });

  }

  sendUpdate(id:number):void{
    
    this.employeeService.getEmployeeById(id).subscribe({
      next: (data) =>{
        this.employee = data;
      },
      error: (err)=>{

      }
    })
    this.updateFlag = true;

  }

  get filteredEmployees() {
  
    if(this.employees){
      return this.employees.filter(employee =>
        employee.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        employee.employee_Id.toString().includes(this.searchTerm)
      );
    }

    return;
    
  }

}
