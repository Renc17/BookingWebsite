import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalLayoutComponent } from './rental-layout.component';

describe('RentalLayoutComponent', () => {
  let component: RentalLayoutComponent;
  let fixture: ComponentFixture<RentalLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
