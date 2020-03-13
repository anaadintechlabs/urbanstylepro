import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SalesListingComponent } from './sales-listing.component';

describe('SalesListingComponent', () => {
  let component: SalesListingComponent;
  let fixture: ComponentFixture<SalesListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SalesListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
