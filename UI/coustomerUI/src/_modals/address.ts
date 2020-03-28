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

interface Country {
    id: number,
    countryName: string, 
    countryCode: string, 
    currency: string
}

interface State {
    id: number, 
    stateName: string, 
    stateCode: string, 
    country: Country
}

interface City {
    id: number, 
    cityName: string, 
    cityCode: string, 
    state: State
}