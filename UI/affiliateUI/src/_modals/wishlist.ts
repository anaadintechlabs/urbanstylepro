import { User } from "src/_modals/user.modal";
import { ProductVariant } from "src/_modals/productVariant";

export interface WishList {
  id: number;
  user: User;
  productVariant: ProductVariant;
  status: number;


}
