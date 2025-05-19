import { Component, Input, OnChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { ApiService } from '../api.service';
import { Router } from '@angular/router';
import { SharedDataService } from '../shared-data.service';
import { NgIf } from '@angular/common';


@Component({
  selector: 'app-viewfiles',
  standalone: true,
  imports: [MatTableModule,MatPaginator,NgIf],
  templateUrl: './viewfiles.component.html',
  styleUrl: './viewfiles.component.css'
})
export class ViewfilesComponent {
  noSearchFound:boolean=false;
  displayedColumns: string[] = ['id', 'filename', 'localDate','status', 'recordCount','errors',"details"];
  dataSource = new MatTableDataSource<PeriodicElement>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  
  ngOnInit() {
    if(localStorage.getItem("jwt")===null){
      this.router.navigate(["/login"]);
    }
    this.dataSource.paginator = this.paginator;
    
    //On search
   this.sharedDataService.searchResults$.subscribe((data)=>{    
       this.dataSource.data=data;
       
   })
    
    this.apiService. getFileList(localStorage.getItem("jwt")).subscribe( 
      {next: (response) => {
        
        this.dataSource.data = response;
       
      },
      error: (error) => console.error('error:', error)}
    )
  }

  ngAfterViewInit(){
    this.dataSource.paginator=this.paginator;
  }
  
  constructor(private apiService:ApiService,private router:Router,private sharedDataService:SharedDataService){
    
  }

  fileDetails(id:number){
     this.router.navigate(['filedetails',id]);
  }
  
}

export interface PeriodicElement {
  id:number;
  filename: string;
  localDate: Date;
  status: string;
  recordCount: number;
  errors:string;
}
let ELEMENT_DATA: PeriodicElement[] = [];