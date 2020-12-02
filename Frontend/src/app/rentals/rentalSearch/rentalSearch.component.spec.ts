import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalSearchComponent } from './rentalSearch.component';

describe('GetRentalsComponent', () => {
  let component: RentalSearchComponent;
  let fixture: ComponentFixture<RentalSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
