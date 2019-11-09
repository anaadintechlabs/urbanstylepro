export interface Category{
    categoryId : number,
    categoryCode : string,
    categoryName : string,
    categoryImage : string,
    categoryVideo : string,
    parentCategory : Category,
    status : string,
    commissionPercent : number,
    fullFillMentPercent: number,
    createdDate : Date,
    createdBy : string,
    modifiedDate : Date,
    modifiedBy : string
}