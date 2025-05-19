import { Component } from '@angular/core';
import { ApiService } from '../api.service';
import { NgFor, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [NgFor,NgIf,FormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  constructor(private apiService:ApiService,private router:Router){}
  userList:any[]=[];
  user:string="";
  idByAdmin:any="";
  deleteSuccessAdmin:boolean=false;
  deleteCancelAdmin:boolean=false;
  deleteAdminConfiramtion:boolean=false;
  username:string="";
  password:string="";
  role:string="Role_User";
  ngOnInit(){

    this.apiService.getAllUsers(localStorage.getItem("jwt")).subscribe({
        next:(response)=>{
            this.userList=response.filter((each:any)=>{
                    if(each.username!==localStorage.getItem("username")){
                      return true
                    }
                    else{
                      return false
                    }
            })
        },
        error:(error)=>{
               console.log(error)
        }
    });

    // this.apiService.addUserByAdmin(localStorage.getItem("jwt"),this.username,this.password,this.role).subscribe({
    //    next:(response)=>{
    //      console.log("Successfully Addede");

    //    },
    //    error:(error)=>{
    //      console.log(error)
    //    }
    // })
    
  }
  confirmDelete(username:any,id:any){
    this.idByAdmin=id;
    this.user=username;
    this.deleteAdminConfiramtion=true;
  }

  cancelAdmin(){
    this.deleteAdminConfiramtion=false;
     this. deleteCancelAdmin=true;

     setTimeout(()=>{
      this.deleteCancelAdmin=false;
     },2000);

  }

  deleteUserByAdmin(){
    this.deleteAdminConfiramtion=false;
    console.log(this.idByAdmin);
    this.apiService.deleteUserByAdmin(localStorage.getItem("jwt"),this.idByAdmin).subscribe({
       next:(response)=>{
          
       },
       error:(error)=>{
        console.log(error);
       }
    })
    
    this.deleteSuccessAdmin=true;
    setTimeout(()=>{
      this.apiService.getAllUsers(localStorage.getItem("jwt")).subscribe({
        next:(response)=>{
            this.userList=response.filter((each:any)=>{
                    if(each.username!==localStorage.getItem("username")){
                      return true
                    }
                    else{
                      return false
                    }
            })
        },
        error:(error)=>{
               console.log(error)
        }
    });
      this.deleteSuccessAdmin=false;
      
    },2500)

  }
}
