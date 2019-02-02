import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckhistoryComponent } from './checkhistory.component';

describe('CheckhistoryComponent', () => {
  let component: CheckhistoryComponent;
  let fixture: ComponentFixture<CheckhistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckhistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckhistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
