import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductHeaderComponent } from './add-product-header.component';

describe('AddProductHeaderComponent', () => {
  let component: AddProductHeaderComponent;
  let fixture: ComponentFixture<AddProductHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
