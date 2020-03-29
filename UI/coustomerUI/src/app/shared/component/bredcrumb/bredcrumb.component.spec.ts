import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BredcrumbComponent } from './bredcrumb.component';

describe('BredcrumbComponent', () => {
  let component: BredcrumbComponent;
  let fixture: ComponentFixture<BredcrumbComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BredcrumbComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BredcrumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
