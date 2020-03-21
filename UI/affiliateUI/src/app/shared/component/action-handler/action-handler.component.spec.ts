import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionHandlerComponent } from './action-handler.component';

describe('ActionHandlerComponent', () => {
  let component: ActionHandlerComponent;
  let fixture: ComponentFixture<ActionHandlerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActionHandlerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionHandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
