import { Test, TestingModule } from '@nestjs/testing';
import { VoiceService } from './voice.service';

describe('VoiceService', () => {
  let service: VoiceService;
  beforeAll(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [VoiceService],
    }).compile();
    service = module.get<VoiceService>(VoiceService);
  });
  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
