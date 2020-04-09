import { User } from './user';

export interface Address {
    id: number,
    addressOne: string,
    addressTwo: string,
    user: User,
    country: Country,
    state: State,
    cite: City,
    zip: string,
    primaryAddress: false  
}

export interface Country {
    id: number,
    countryName: string, 
    countryCode: string, 
    currency: string
}

export interface State {
    id: number, 
    stateName: string, 
    stateCode: string, 
    country: Country
}

export interface City {
    id: number, 
    cityName: string, 
    cityCode: string, 
    state: State
}