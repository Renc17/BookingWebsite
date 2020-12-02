import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetRentalsByOwnerComponent } from './get-rentals-by-owner.component';

describe('GetRentalsByOwnerComponent', () => {
  let component: GetRentalsByOwnerComponent;
  let fixture: ComponentFixture<GetRentalsByOwnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetRentalsByOwnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetRentalsByOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
