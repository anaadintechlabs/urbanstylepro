import { Component, OnInit, Input } from '@angular/core';
import { Filter, checkbox } from 'src/_modals/filter';
import { ProductService } from 'src/_service/product/product.service';

@Component({
  selector: 'shop-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss']
})
export class FiltersComponent implements OnInit {

  @Input() filters : Object = {};
  // preparedFilters : Filter[] = [];

  constructor(
    public _productService : ProductService,
  ) { }

  ngOnInit(): void {
    this.prepareFilters();
  }

  prepareFilters() {
    let tempFilter : Filter = {};
    if(Object.keys(this.filters).length) {
      for (const filter in this.filters) {
        if (this.filters.hasOwnProperty(filter)) {
          tempFilter.id = parseInt(filter)
          const element = this.filters[filter];
          for (const item in element) {
            if (element.hasOwnProperty(item)) {
              tempFilter.name = item;
              const ele = element[item];
              tempFilter.value = this.getUnique(ele);
            }
          }
          this._productService.preparedFilters.push(tempFilter);
          tempFilter = {}
        }
      }
      console.log("alldata",this._productService.preparedFilters);
    }
  }

  changeStatus(item : checkbox) {
    item.checked = !item.checked;
  }

  applyFilter() {
    console.log("final data ===== >", this._productService.preparedFilters);
  }

  public getUnique(array:[]){
    var uniqueArray : checkbox[] = [];
    var tempArray =  [];
    
    // Loop through array values
    for(let i=0; i < array.length; i++){
        if(tempArray.indexOf(array[i]) === -1) {
          tempArray.push(array[i]);
        }
    }
    tempArray.forEach(element => {
      let obj : checkbox = {}
      obj.name = element;
      obj.checked = false;
      uniqueArray.push(obj);
    });
    return uniqueArray;
  }

}
