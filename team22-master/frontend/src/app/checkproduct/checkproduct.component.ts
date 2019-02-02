import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CheckproductService} from '../service/checkproduct.service';
import {MatSnackBar} from '@angular/material';

export interface Tile {
  cols: number;
  rows: number;
}
export interface StockElement {
  productID: String;
  productName: String;
  productPrice: number;
  productQuantity: number;
  type: {
    typeName: String;
  };
  status: {
    statusProduct: String;
  };
  productDate: Date;
}
@Component({
  selector: 'app-checkproduct',
  templateUrl: './checkproduct.component.html',
  styleUrls: ['./checkproduct.component.css']
})
export class CheckproductComponent implements OnInit {
  tiles: Tile[] = [
    {cols: 1, rows: 1, },
  ];
  tile_right: Tile[] = [
    {cols: 2, rows: 1, },
  ];
  max = 100;
  min = 0;
  step = 1;
  type: Array<any>;
  status: Array<any>;
  product: Array<any>;
  checkproduct: Array<any>;
  views: any = {
    level : 0,
    comment: '',
    productID: '',
    productName: '',
    productQuantity: '',
    productPrice : '',
    prodID : '',
    checkId:'',
    selectProductID: '',
    selectProductName: '',
    selectProductQuantity: '',
    selectProductPrice : '',
    selectPID: '',
    selectCheckProductComment: '',
    selectCheckProductLevel: '',
    selectCheckId:''
  };
  displayedColumns: string[] = ['PID', 'productID', 'productName', 'productPrice', 'productQuantity', 'types','statuses'];
  displayedColumnss: string[] = ['PID', 'productID','productName','CID','level', 'comment'];
  constructor(private CheckService: CheckproductService,private snackBar: MatSnackBar, private httpClient: HttpClient) {
  }
  ngOnInit() {
    this.CheckService.getType().subscribe(data => {
      this.type = data;
      console.log(this.type);
    });
    this.CheckService.getStatus().subscribe(data => {
      this.status = data;
      console.log(this.status);
    });
    this.CheckService.getProduct().subscribe(data => {
      this.product = data;
      console.log(this.product);
    });
    this.CheckService.getCheckProduct().subscribe(data => {
      this.checkproduct = data;
      console.log(this.checkproduct);
    });
  }
   selectRow(row) {
    this.views.selectPID = row.prodId;
    this.views.selectProductID = row.productIds;
    this.views.selectProductName = row.productName;
    this.views.selectProductPrice = row.productPrice;
    this.views.selectProductQuantity = row.productQuantity;
    console.log(this.views.selectPID);
    console.log( this.views.selectProductName);
    console.log(this.views.selectProductPrice);
    console.log( this.views.selectProductQuantity);
    console.log(this.views.selectProductID);
  }
  electRow(row) {
    this.views.selectPID = row.product.prodId;
    this.views.selectCheckProductLevel = row.checkLevel;
    this.views.selectCheckProductComment = row.checkComment;
    this.views.selectCheckId = row.checkId;
    console.log(this.views.selectCheckId);
    console.log(this.views.selectPID);
    console.log(this.views.selectCheckProductComment);
    console.log(this.views.selectCheckProductLevel);
  }
  save() {
    this.views.prodID = this.views.selectPID;
    this.httpClient.post('http://localhost:8080/checkproduct/' 
    + this.views.prodID + '/' + this.views.level + '/' + this.views.comment,
    this.views,) .subscribe(
      data => {
        this.snackBar.open('Check ', 'complete', {
        });
        console.log('INPUT Request is successful', data);
      },
      error => {
        this.snackBar.open('Check ', 'uncomplete', {
        });
        console.log('Error', error);
      }
    );
  }
  editcheck() {
    this.views.prodID = this.views.selectPID;
    this.views.checkId = this.views.selectCheckId;
    if (!this.views.level) {
      this.views.level = this.views.selectCheckProductLevel;
    }
    if (!this.views.comment) {
      this.views.comment = this.views.selectCheckProductComment;
    }
    console.log(this.views.prodID);
    console.log(this.views.checkId);
    console.log(this.views.level);
    console.log(this.views.comment);
    this.httpClient.put('http://localhost:8080/checkproduct/editcheck/'+ this.views.prodID + '/' + this.views.checkId + '/'
    + this.views.level + '/' + this.views.comment,
    this.views)  .subscribe(
      data => {
        if (data)  {
          this.snackBar.open('edit detail ', 'complete', {
          });
          console.log('Success');
        }
      },
      error => {
        this.snackBar.open('edit detail', 'uncomplete', {
        });
        console.log('Uncomplete',  error);
      }
    );
  }
}