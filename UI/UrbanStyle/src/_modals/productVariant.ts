import { User } from "src/_modals/user.modal";
import { Product } from "src/_modals/product";

export interface ProductVariant {
  productVariantId: number;
  sku: string;
  prodName: string;
  prodDesc: string;
  mainImageUrl: string;
  user: User;
  displayPrice: number;
  actualPrice: number;
  discountPrice: number;
  totalQuantity: number;
  reservedQuantity: number;
  product: Product;
    status: number;
}
