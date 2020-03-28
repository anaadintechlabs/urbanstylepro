export interface FilterRequest {
    searchString ?: string;
    catId ?: number;
    filterData ?: any[];
    limit ?: number;
    offset ?: number;
}

export interface Filter {
    id ?: number;
    name ?: string;
    value ?: checkbox[];
} 

export interface checkbox {
    name ?: string;
    checked ?: boolean;
}