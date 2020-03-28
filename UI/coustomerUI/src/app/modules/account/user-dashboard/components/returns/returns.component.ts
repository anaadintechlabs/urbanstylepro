import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../../../../_service/product/order.service';

@Component({
  selector: 'app-returns',
  templateUrl: './returns.component.html',
  styleUrls: ['./returns.component.scss']
})
export class ReturnsComponent implements OnInit {

  public offset=0;
  public limit=15;
  public sortingDirection='DESC';
  public sortingField='createdDate'
  public returnList:any;
  public returnDetails:any;

  constructor(public orderService:OrderService) { }

  ngOnInit(): void {
    this.getReturnByUser();
  }

  getReturnByUser()
  {
    let filter={
      'offset':this.offset,
      'limit':this.limit,
      'sortingDirection':this.sortingDirection,
      'sortingField':this.sortingField
  
    }
    this.orderService.getReturnByUser(filter).subscribe(data=>{
      console.log("data us",data);
      this.returnList=data.returnList;
      console.log(this.returnList)
    },error=>{
      console.log("error",error);
    })
  }


  getAllDetailOfReturn(returnId)
  {
    this.orderService.getAllDetailOfReturn(returnId).subscribe(data=>{
      this.returnDetails=data.returnDetails;
     },error=>{
       console.log("error",error);
     }) 
  }


  
}
