import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VaultsComponent } from './vaults.component';

describe('VaultsComponent', () => {
  let component: VaultsComponent;
  let fixture: ComponentFixture<VaultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VaultsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VaultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
