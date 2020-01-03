import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { AdminComponent } from "src/app/admin/admin.component";
import { CategroryComponent } from "src/app/admin/categrory/categrory.component";
import { AddCategoryComponent } from "src/app/_forms/add-category/add-category.component";


export const adminRoutes: Routes=[
    {
        path:'',
        component : AdminComponent,
        children : [
           {
                path : 'category',
                component : CategroryComponent
            },

            {
                path : 'category/:action',
                component : AddCategoryComponent
            },
            {
                path : 'category/:action/id',
                component : AddCategoryComponent
            },
           
        ]
    }
];
@NgModule({
  imports: [RouterModule.forRoot(adminRoutes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }