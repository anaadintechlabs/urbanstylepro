export interface User {
    id: number
    name: string
    email: string
    bio: string
    imageUrl: string
    phoneNumber: string
    emailVerified: boolean
    userType: UserType
    provider: string
    providerId: null
    blocked: boolean
    joinDate: Date
    enableMobileNumber: boolean
    deactivated: boolean
    deactivatedMessage: string
    deactivatedDate: Date
    firstTimeLogin: boolean
    token : string
}

export enum UserType {

}