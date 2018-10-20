import { Get, Controller, Logger } from '@nestjs/common';
import { AppService } from './app.service';
import { DialogflowService } from './services/dialogflow/dialogflow.service';
import { VoiceService } from './services/voice/voice.service';
import { AvatarService } from './services/avatar/avatar.service';

@Controller()
export class AppController {
  constructor(
    private readonly appService: AppService,
    private readonly dialogflowService: DialogflowService,
    private readonly voiceService: VoiceService,
    private readonly avatarService: AvatarService,
  ) {}

  @Get()
  async root(): Promise<string> {
    const germanGreeting = await this.dialogflowService.detectIntent('/start', 'de-DE');
    await this.avatarService.speak().then(() => {
      Logger.log('Avatars speaks');
    });
    await this.voiceService.talk('de-DE', germanGreeting, false).then(((value) => {
      Logger.log('Voice talks');
    }));

    await this.avatarService.idle().then(() => {
      Logger.log('Avatars idling');
    });

    const englishGreeting = await this.dialogflowService.detectIntent('/start', 'en-US');
    await this.avatarService.speak().then(() => {
      Logger.log('Avatars speaks');
    });
    await this.voiceService.talk('en-US', englishGreeting, false).then(((value) => {
      Logger.log('Voice talks');
    }));

    await this.avatarService.idle().then(() => {
      Logger.log('Avatars idling');
    });

    const spanishGreeting = await this.dialogflowService.detectIntent('/start', 'es-ES');
    await this.avatarService.speak().then(() => {
      Logger.log('Avatars speaks');
    });
    await this.voiceService.talk('es-ES', englishGreeting, false).then(((value) => {
      Logger.log('Voice talks');
    }));

    await this.avatarService.idle().then(() => {
      Logger.log('Avatars idling');
    });

    const intro = await this.dialogflowService.detectIntent('/gender', 'de-DE');
    await this.avatarService.speak().then(() => {
      Logger.log('Avatars speaks');
    });
    await this.voiceService.talk('de-DE', intro, false).then(((value) => {
      Logger.log('Voice talks');
    }));

    await this.avatarService.follow().then(() => {
      Logger.log('Avatars follows');
    });


    
    return "INTRO IS FINISHED";
  }
}
