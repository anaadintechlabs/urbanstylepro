import { ProductVerient } from './product';

export interface CartItem {
    product: ProductVerient;
    options: {
        name: string;
        value: string;
    }[];
    quantity: number;
}