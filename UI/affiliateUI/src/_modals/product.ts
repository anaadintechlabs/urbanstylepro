import { User } from "src/_modals/user.modal";

export interface Product {
  productId: number,
  productCode: string,
  productName: string,
  categoryId: number,
  user: User,
  brandName: string,
  manufacturer: string,
  primaryAddress: boolean,
  status: number,
}
