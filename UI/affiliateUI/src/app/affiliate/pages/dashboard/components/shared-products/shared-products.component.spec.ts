import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedProductsComponent } from './shared-products.component';

describe('SharedProductsComponent', () => {
  let component: SharedProductsComponent;
  let fixture: ComponentFixture<SharedProductsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SharedProductsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SharedProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
