import { Country, State, City } from "src/_modals/country";
import { User } from "src/_modals/user.modal";

export interface Address {
  id: number,
  addressOne: string,
  addressTwo: string,
  country: Country,
  state: State,
  cite: City,
  user: User,
  zip: string,
  primaryAddress: boolean,
  status: number,
}
