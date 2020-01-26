import { Component, OnInit, EventEmitter, Output, Input } from "@angular/core";
import { AddProductService } from 'src/_services/product/addProductService';
import { Router } from '@angular/router';

@Component({
  selector: "product-media",
  templateUrl: "./add-product-media.component.html",
  styleUrls: ["./add-product-media.component.scss"]
})
export class AddProductMediaComponent implements OnInit {
  @Output() myFilesEmit: EventEmitter<string[]> = new EventEmitter<string[]>();
  @Output() urlArrayEmit: EventEmitter<any> = new EventEmitter<any>();
  @Input() myFiles: string[] = [];
  @Input() urlArray: any = [];
  constructor(
    private _addProduct : AddProductService,
    private _router:Router
  ) {}

  ngOnInit() {}

  onSelectFile(event) {
    for (var i = 0; i < event.target.files.length; i++) {
      if (event.target.files[i].size <= 2048000) {
        this.myFiles.push(event.target.files[i]);
        var reader = new FileReader();
        reader.readAsDataURL(event.target.files[i]);
        reader.onload = event => {
          this.urlArray.push(reader.result);
          this.urlArrayEmit.emit(this.urlArray);
          this.myFilesEmit.emit(this.myFiles);
        };
      } else {
        alert("Please select image less than 2MB.");
      }
    }
    console.log("total imags" + this.myFiles);
  }

  nextStep() {
    console.log("myfile",this.myFiles);
    if(this.myFiles.length==0)
      {
alert("Please enter atleast one image")
      }
      else
        {
    this._router.navigateByUrl('/vendor/addProduct/metaInfo');
        }
  }

  cancelButton()
  {
     this._router.navigateByUrl('/vendor/addProduct/prodDesc');
  }
}
