import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlockCategoryComponent } from './block-category.component';

describe('BlockCategoryComponent', () => {
  let component: BlockCategoryComponent;
  let fixture: ComponentFixture<BlockCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlockCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
