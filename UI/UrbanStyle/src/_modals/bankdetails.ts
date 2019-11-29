import { User } from "src/_modals/user.modal";

export interface BankDetails {
  id: number,
  accType: string,
  accountNumber: string,
  bankName: string,
  ifscCode:string,
  user: User,
  status: number,
}
