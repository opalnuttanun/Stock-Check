import {Component, OnInit ,ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CheckhistoryService} from '../service/checkhistory.service';
import {MatSnackBar} from '@angular/material';
import {DatePipe} from '@angular/common';
import {MatSort} from '@angular/material';

@Component({
  selector: 'app-checkhistory',
  templateUrl: './checkhistory.component.html',
  styleUrls: ['./checkhistory.component.css']
})
export class CheckhistoryComponent implements OnInit {
  tiles: Tile[] = [
    {cols: 1, rows: 1, },
  ];
  tile_right: Tile[] = [
    {cols: 2, rows: 1, },
  ];
  product: Array<any>;
  checkproduct: Array<any>;
  checkhistoryDate: Array<any>;
  selectcheckhistoryDate: Array<any>;
  pipe = new DatePipe('th');

  @ViewChild(MatSort)
  sort: MatSort;
  views: any = {
    checkId:'',
    selectProductID: '',
    selectProductName: '',
    selectCheckProductComment: '',
    selectCheckProductLevel: '',
    selectCheckProductID: ''
  };
  displayedColumns: string[] = ['CID', 'productID','productname', 'level', 'comment'];
  constructor(private CheckhistoryService: CheckhistoryService,private snackBar: MatSnackBar, private httpClient: HttpClient) {
  }
  
  ngOnInit() {
    this.CheckhistoryService.getProduct().subscribe(data => {
      this.product = data;
      console.log(this.product);
    });
    this.CheckhistoryService.getCheckProduct().subscribe(data => {
      this.checkproduct = data;
      console.log(this.checkproduct);
    });
  }
 
   selectRow(row) {
    this.views.selectCheckProductID = row.checkId;
    this.views.selectProductID = row.product.productIds;
    this.views.selectProductName = row.product.productName;
    this.views.selectCheckProductComment = row.checkComment;
    this.views.selectCheckProductLevel = row.checkLevel;
    console.log( this.views.selectProductName);
    console.log(this.views.selectCheckProductID);
    console.log( this.views.selectCheckProductComment);
    console.log(this.views.selectProductID);
    console.log(this.views.selectCheckProductLevel);
  }
  delete() {
    this.httpClient.delete('http://localhost:8080/checkhistory/' + this.views.checkID )
    .subscribe(
      data => {
        this.snackBar.open('delete', 'complete', {
        });
        console.log('Delete Request is successful', data);
      },
      error => {
        this.snackBar.open('delete', 'uncomplete', {
        });
        console.log('Error', error);
      }
    );
  }
  savehistory() {
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
}
export interface Tile {
  cols: number;
  rows: number;
}