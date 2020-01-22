import { Injectable } from '@angular/core';
import { Product } from 'src/_modals/product';

@Injectable(
    {providedIn: 'root'}
)

export class AddProductService {
    public addProduct : any = {};
    constructor() { }
    
}