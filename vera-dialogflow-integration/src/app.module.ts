import { Module, HttpModule } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ListenController } from './controllers/listen/listen.controller';
import { AvatarService } from './services/avatar/avatar.service';
import { VoiceService } from './services/voice/voice.service';
import { DialogflowService } from './services/dialogflow/dialogflow.service';

@Module({
  imports: [HttpModule],
  controllers: [AppController, ListenController],
  providers: [AppService, AvatarService, VoiceService, DialogflowService],
})
export class AppModule {}
