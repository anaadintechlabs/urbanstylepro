import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CartSideMenuComponent } from './cart-side-menu.component';

describe('CartSideMenuComponent', () => {
  let component: CartSideMenuComponent;
  let fixture: ComponentFixture<CartSideMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CartSideMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CartSideMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
