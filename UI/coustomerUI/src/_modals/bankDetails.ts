import { User } from './user';

export interface BankDetails  {
    id: number
    user: User
    bankName: string
    accountNumber: string
    accType: string
    ifscCode: string
}