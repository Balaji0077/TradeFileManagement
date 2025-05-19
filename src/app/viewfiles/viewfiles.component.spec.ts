import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewfilesComponent } from './viewfiles.component';

describe('ViewfilesComponent', () => {
  let component: ViewfilesComponent;
  let fixture: ComponentFixture<ViewfilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewfilesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewfilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
