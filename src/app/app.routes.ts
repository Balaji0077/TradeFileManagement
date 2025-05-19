import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

import { HomeComponent } from './home/home.component';
import { UploadfileComponent } from './uploadfile/uploadfile.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FiledetailsComponent } from './filedetails/filedetails.component';
import { UpdateComponent } from './update/update.component';
import { AdminComponent } from './admin/admin.component';

export const routes: Routes = [{
    path:"login",
    component: LoginComponent
    },
    {
        path:"register",
        component:RegisterComponent
    },
    {
        path:"dashboard",
        component:DashboardComponent
   },
    {
        path:"upload",
        component:UploadfileComponent
    },
    {
        path:"filedetails/:id",
        component:FiledetailsComponent
    },
    { 
         path:"update/:id",
         component:UpdateComponent
    },
    {   path:"admin",
        component:AdminComponent

    },
    {
        path:"",
        component:HomeComponent
    }
    
   
];
