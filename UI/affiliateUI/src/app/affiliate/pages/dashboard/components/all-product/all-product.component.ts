import { Component, OnInit } from '@angular/core';
import { ProductVariant } from 'src/_modals/productVariant';
import { DataService } from 'src/_services/data/dataService';
import { User } from 'src/_modals/user.modal';

@Component({
  selector: 'app-all-product',
  templateUrl: './all-product.component.html',
  styleUrls: ['./all-product.component.scss']
})
export class AllProductComponent implements OnInit {
  
  productList : ProductVariant[] = [];
  generatedLinks:any[]=[];
  user : User;
  userId :any;
  constructor(
    private dataService : DataService
  ) {
    this.user = JSON.parse(window.localStorage.getItem("user"));
    if (this.user.token) {
      this.userId = this.user.id;
      this.getGeneratedCodeAndLinkOfAffiliate(this.userId);
     
    }
   }

  ngOnInit() {
  }

  getGeneratedCodeAndLinkOfAffiliate(userId)
  {
    let url = `product/getGeneratedCodeAndLinkOfAffiliate?userId=`+userId;
    this.dataService.getGeneratedCodeAndLinkOfAffiliate(url).subscribe(data => {
          this.generatedLinks = data;
          
          console.log(this.generatedLinks);
           this.getAllActiveProduct();
      }, error => {
          console.log("error======", error);
      });
  }
  getAllActiveProduct() {
    let body = {
      limit: 15,
      offset: 0,
      sortingDirection: "DESC",
      sortingField: "modifiedDate"
    };

    let url = `product/getAllVariantsByStatus?status=1`;
    this.dataService.getAllActiveProduct(url, body).subscribe(data => {
          this.productList = data;
         console.log("generate Linked",this.generatedLinks);
          this.productList.forEach(element => {
            if(this.generatedLinks && this.generatedLinks.length>0)
              {
              let index=  this.generatedLinks.findIndex(obj =>
                { 
                  return obj.id == element.productVariantId
                });
                console.log("index",index);
                if(index!=-1)
                  {
                    if(index==0)
                      {
                element['generatedLink']=this.generatedLinks[0].generatedLink;        
                      }
                    else
                      {
                      element['generatedLink']=this.generatedLinks[index-1].generatedLink;
                      }
                  }
               console.log('elemnt is',element);
              }
          });
      }, error => {
          console.log("error======", error);
      });
    console.log(this.productList);
  }

}
