import { Category } from './category.modal';

export interface CategoryAttribute {
    id : number;
	attributeMaster : AttributeMaster;
	category : Category;
	status : number;
	allAttributeMap : allAtrrtibure;
}

export interface AttributeMaster{
    id : number;
	variationName : string;
	variationType : string;// Like Input,Checkbox,Select Etc
	variationDefaultValue : string;
	typeOfInput : string; //If variationType is input then text,number or what
	variantDummy : string; //Showing Size dummy like XL,XXL label show
	status : number;
	checked?: boolean;
}

export class allAtrrtibure {
 	variationName : string;
	variationAttribute : string[];

	constructor() {
		this.variationName ='';
		this.variationAttribute = [];
	}
  }