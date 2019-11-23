import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder, FormArray } from '@angular/forms';

@Injectable({
    providedIn: 'root',
})
export class AddProductService {

    /////// FormGroup for productmeta (key value pair)
    public productMetaForm = new FormGroup({
        metaKey : new FormControl('', [Validators.required]),
        metaValue: new FormControl('',[Validators.required])
    });

    /////// product variation formGroup
    public productVariantForm = new FormGroup({
        sku : new FormControl('', [Validators.required]),
        displayPrice: new FormControl('',[Validators.required]),
        actualPrice : new FormControl('', [Validators.required]),
        discountPrice : new FormControl('', [Validators.required]),
        totalQuantity :new FormControl('', [Validators.required]), 
        reservedQuantity:new FormControl('',[Validators.required])
    });

    /////// formGroup for vital information for product
    public productForm = new FormGroup({
        productId : new FormControl('', []),
        productCode: new FormControl('',[Validators.required]),
        categoryId: new FormControl('',[Validators.required]),
        productName : new FormControl('', [Validators.required]),
        brandName : new FormControl('', []),
        manufacturer :new FormControl('', []), 
        coverPhoto:new FormControl('',[])
    });

    product : FormGroup;
    productDTO : FormGroup;

    constructor(
        protected _fb : FormBuilder,
    ) {
        this.productDTO = this._fb.group({
            'product' : this.productForm,
            'productVariantDTO' : this._fb.array([this.initializeProductVarientDto()]),
            'productMetaInfo' : this._fb.array([this.addMetaInfo()]),
        });
        console.log(this.productDTO.value);
    }



    public initializeProductVarientDto() : FormGroup {
        let productVarientDto : FormGroup;
        productVarientDto = this._fb.group({
            'attributeMap' : new FormControl('',[]),
            'productVariant' : this.productVariantForm,
        })
        return productVarientDto;
    }

    public addMetaInfo() : FormGroup {
        return this.productMetaForm;
    }

    get productFormGroup() : FormGroup {
        return this.productDTO.get('product') as FormGroup;
    }

    get productVariantDTO() : FormArray {
        return this.productDTO.get('productVariantDTO') as FormArray;
    }

}