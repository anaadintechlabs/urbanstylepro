import { UserType, User } from './user';
import { Url } from 'url';

export interface product {
    productId: number
    productCode: string
    productIdType: string
    productName: string
    categoryId: number
    status: number
    brandName: string
    manufacturer: string
    defaultSize: string
    defaultColor: string
    variantExist: boolean
    createdDate: Date
    createdBy: UserType
    modifiedDate: Date
    modifiedBy: Date
    totalVarients: number
    user: User
}

export interface ProductVerient {
    productVariantId: number
    uniqueprodvarId : string
    categoryId: number
    product: product
    productIdType: string
    sku: string
    prodName: string
    prodDesc: string
    mainImageUrl: string
    displayPrice: number
    actualPrice: number
    discountPrice: number
    totalQuantity: number
    reservedQuantity: number
    status: number
    createdDate: Date
    createdBy: UserType
    modifiedDate: Date
    modifiedBy: string
    fetauredProduct: boolean
    dealOfTheDay: boolean
}

