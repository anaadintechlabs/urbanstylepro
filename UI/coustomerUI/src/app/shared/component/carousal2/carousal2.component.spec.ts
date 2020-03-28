import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Carousal2Component } from './carousal2.component';

describe('Carousal2Component', () => {
  let component: Carousal2Component;
  let fixture: ComponentFixture<Carousal2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Carousal2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Carousal2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
