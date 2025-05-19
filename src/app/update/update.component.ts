import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [NgIf,FormsModule],
  templateUrl: './update.component.html',
  styleUrl: './update.component.css'
})
export class UpdateComponent {

 dataObj:any = {};
 updatedStatus:string="";
 currentStatus:string="" ;
 newStatus:boolean=false;
 failedStatus:boolean=false;
 processedStatus:boolean=false;
 constructor(private route: ActivatedRoute,private apiService:ApiService,private router:Router){
  let id:any = this.route.snapshot.paramMap.get('id');
  this.apiService.getFileById(localStorage.getItem("jwt") ,id).subscribe(
    {
      next: (response) => {
        this.dataObj=response;
        this.currentStatus=response.status;
        if(this.currentStatus==="NEW"){
          this.failedStatus=true;
          this.processedStatus=true;
          this. updatedStatus="FAILED";
        }
        if(this.currentStatus==="FAILED"){
          this.newStatus=true;
          this.processedStatus=true;
          this. updatedStatus="NEW";
        }
        if(this.currentStatus==="PROCESSED"){
           this.newStatus=true;
           this.failedStatus=true;
           this. updatedStatus="NEW";
        }
        
        },
      error: (error) => console.error(error)
    }
   )

   
 }

 ngOnInit(){
  if(localStorage.getItem("jwt")===null){
    this.router.navigate(["/login"]);
  }
 }
 okUpdateStatus(){
  let id:any = this.route.snapshot.paramMap.get('id');
  this.apiService. updateStatusById(localStorage.getItem("jwt") ,id, this.updatedStatus).subscribe(
    {
      next: (response) => {
          
           this.router.navigate(['/dashboard'],{state:{updateDialog:true,filename:this.dataObj.filename, updatedStatus:this.updatedStatus}});
        },
      error: (error) => console.error(error)
    }
   )
   
}
}
