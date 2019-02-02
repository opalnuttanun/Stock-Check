import { TestBed } from '@angular/core/testing';

import { CheckhistoryService } from './checkhistory.service';

describe('CheckhistoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CheckhistoryService = TestBed.get(CheckhistoryService);
    expect(service).toBeTruthy();
  });
});
