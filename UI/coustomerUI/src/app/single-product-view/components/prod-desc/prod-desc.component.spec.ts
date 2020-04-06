import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdDescComponent } from './prod-desc.component';

describe('ProdDescComponent', () => {
  let component: ProdDescComponent;
  let fixture: ComponentFixture<ProdDescComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProdDescComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProdDescComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
