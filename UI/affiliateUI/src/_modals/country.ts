export interface Country {
    id : number;
	countryName : string;
	countryCode : string;
	currency : string;
}


export interface State {
    id : number;
	stateName : string;
	stateCode : string;
	country : Country;
}


export interface City {
    id : number;
	cityName : string;
	cityCode : string;
	state : City;
}
