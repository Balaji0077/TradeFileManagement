import { Component } from '@angular/core';
import { ViewfilesComponent } from '../viewfiles/viewfiles.component';
import { Router } from '@angular/router';
import { SearchComponent } from '../search/search.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ViewfilesComponent,SearchComponent,NgIf],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  deleteCancel:boolean=false;
  deleteSuccess:boolean=false;
  filename:string="";
  updatedStatus:string="";
  updateStatusPopup:boolean=false;
  fileUploadPopup:boolean=false;
  
  constructor(private route:Router){}

   ngOnInit(){
    if(localStorage.getItem("jwt")===null){
      this.route.navigate(["/login"]);
    }

    this.fileUploadPopup = window.history.state.fileSuccess;
    this.updateStatusPopup=window.history.state.updateDialog;
    this.filename = window.history.state.filename;
    this.updatedStatus=window.history.state.updatedStatus;
    this.deleteSuccess=window.history.state.deleteSuccessPopup;
    this.deleteCancel=window.history.state.deleteCancelPopup;

    setTimeout(()=>{
      this.fileUploadPopup=false;
      this.deleteSuccess=false;
      this.deleteCancel=false;
    },2500)
    setTimeout(()=>{
      this.updateStatusPopup=false;
    },3500)
   }

   
}
