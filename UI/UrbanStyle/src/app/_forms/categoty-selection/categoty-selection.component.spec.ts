import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategotySelectionComponent } from './categoty-selection.component';

describe('CategotySelectionComponent', () => {
  let component: CategotySelectionComponent;
  let fixture: ComponentFixture<CategotySelectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategotySelectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategotySelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
