import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiledetailsComponent } from './filedetails.component';

describe('FiledetailsComponent', () => {
  let component: FiledetailsComponent;
  let fixture: ComponentFixture<FiledetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiledetailsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FiledetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
