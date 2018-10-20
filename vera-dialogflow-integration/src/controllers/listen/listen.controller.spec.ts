import { Test, TestingModule } from '@nestjs/testing';
import { ListenController } from './listen.controller';

describe('Listen Controller', () => {
  let module: TestingModule;
  beforeAll(async () => {
    module = await Test.createTestingModule({
      controllers: [ListenController],
    }).compile();
  });
  it('should be defined', () => {
    const controller: ListenController = module.get<ListenController>(ListenController);
    expect(controller).toBeDefined();
  });
});
