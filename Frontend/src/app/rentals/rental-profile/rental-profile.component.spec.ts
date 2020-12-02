import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalProfileComponent } from './rental-profile.component';

describe('RentalProfileComponent', () => {
  let component: RentalProfileComponent;
  let fixture: ComponentFixture<RentalProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentalProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentalProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
