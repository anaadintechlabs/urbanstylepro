import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeatueDetailsComponent } from './featue-details.component';

describe('FeatueDetailsComponent', () => {
  let component: FeatueDetailsComponent;
  let fixture: ComponentFixture<FeatueDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeatueDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeatueDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
