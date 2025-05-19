import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../api.service';
import { SharedDataService } from '../shared-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  
  
  filename:string="";
  status:string="";
  id:any;
  fromDate:any;
  toDate:any;
  

  constructor(private apiService:ApiService,private sharedDataService:SharedDataService,private route:Router){

  }

  ngOnInit(){
    if(localStorage.getItem("jwt")===null){
      this.route.navigate(["/login"]);
    }
  }

  applyFilter(){
    
    if(this.filename==""&&this.status==""&&this.id==null&&this.fromDate==null&&this.toDate==null){
      alert("No Filter Applied");
    }
    else
    {
    this.apiService.searchByField(localStorage.getItem("jwt"),this.filename,this.status,this.id,this.fromDate,this.toDate).subscribe({
      next:(response)=>{
        console.log(response);
        this.sharedDataService.updateSearchResults(response);
      },
      error:(error)=>{
         console.log(error)
      }
    });
  }
  }

  clearFilter(){
    this.filename="";
    this.status="";
    this.id=null;
    this.fromDate=null;
    this.toDate=null;
    this.apiService.searchByField(localStorage.getItem("jwt"),"","",null,null,null).subscribe({
      next:(response)=>{
        console.log(response);
        this.sharedDataService.updateSearchResults(response);
      },
      error:(error)=>{
         console.log(error)
      }
    })
  }



}
