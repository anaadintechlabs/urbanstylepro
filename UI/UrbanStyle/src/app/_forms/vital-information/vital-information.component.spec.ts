import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VitalInformationComponent } from './vital-information.component';

describe('VitalInformationComponent', () => {
  let component: VitalInformationComponent;
  let fixture: ComponentFixture<VitalInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VitalInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VitalInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
