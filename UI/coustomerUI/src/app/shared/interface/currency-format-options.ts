export interface CurrencyFormatOptions {
    code?: string;
    display?: 'code' | 'symbol' | 'symbol-narrow' | string | boolean;
    digitsInfo?: string;
    locale?: string;
}