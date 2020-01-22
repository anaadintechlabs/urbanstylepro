import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductDesciprionComponent } from './add-product-desciprion.component';

describe('AddProductDesciprionComponent', () => {
  let component: AddProductDesciprionComponent;
  let fixture: ComponentFixture<AddProductDesciprionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductDesciprionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductDesciprionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
