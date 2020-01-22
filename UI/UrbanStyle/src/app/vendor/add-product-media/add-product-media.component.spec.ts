import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductMediaComponent } from './add-product-media.component';

describe('AddProductMediaComponent', () => {
  let component: AddProductMediaComponent;
  let fixture: ComponentFixture<AddProductMediaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductMediaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductMediaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
