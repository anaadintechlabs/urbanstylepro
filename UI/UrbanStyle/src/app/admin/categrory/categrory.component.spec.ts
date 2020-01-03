import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategroryComponent } from './categrory.component';

describe('CategroryComponent', () => {
  let component: CategroryComponent;
  let fixture: ComponentFixture<CategroryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategroryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategroryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
