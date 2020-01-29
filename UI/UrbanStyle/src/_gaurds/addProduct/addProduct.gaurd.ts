import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router } from '@angular/router';
import { AddProductService } from 'src/_services/product/addProductService';

@Injectable({providedIn: 'root'})
export class AddProductGuard implements CanActivate {
    constructor(
        private _addProduct : AddProductService,
        private _router : Router
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        console.log((JSON.parse(window.sessionStorage.getItem('addProduct'))).product.categoryId);
        let data = (JSON.parse(window.sessionStorage.getItem('addProduct'))).product.categoryId;
        if(data){
            return true;
        } else {
            this._router.navigateByUrl('/vendor/addProduct/categorySelection');
            return false;
        }
    }
}