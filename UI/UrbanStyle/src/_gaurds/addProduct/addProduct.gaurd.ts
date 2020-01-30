import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';

@Injectable({providedIn: 'root'})
export class AddProductGuard implements CanActivate {
    constructor(
        private _addProduct : AddProductService,
        private _router : Router
    ) { }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Promise<boolean> {
        return new Promise<boolean>(resolve=>{
            let status : boolean;
            setTimeout(() => {
                let data = (JSON.parse(window.sessionStorage.getItem('addProduct'))).product.categoryId;
                if(data){
                    resolve(true);
                } else {
                    this._router.navigateByUrl('/vendor/addProduct/categorySelection');
                    resolve(false);
                }            
            }, 200);
        })
    }
}