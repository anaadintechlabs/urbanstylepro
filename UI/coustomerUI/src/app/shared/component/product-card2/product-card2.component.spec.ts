import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCard2Component } from './product-card2.component';

describe('ProductCard2Component', () => {
  let component: ProductCard2Component;
  let fixture: ComponentFixture<ProductCard2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductCard2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductCard2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
