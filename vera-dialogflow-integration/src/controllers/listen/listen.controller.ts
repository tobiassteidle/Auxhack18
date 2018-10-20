import { Controller, Post, Body, Request, Logger } from '@nestjs/common';
import { ListenableFuture } from '../../models/listenable-future.dto';
import { DialogflowService } from '../../services/dialogflow/dialogflow.service';
import { VoiceService } from '../../services/voice/voice.service';
import { AvatarService } from '../../services/avatar/avatar.service';

@Controller('listen')
export class ListenController {
  constructor(
    private readonly dialogflowService: DialogflowService,
    private readonly voiceService: VoiceService,
    private readonly avatarService: AvatarService,
  ) {}
  @Post()
  private async onListen(
    @Body() body: ListenableFuture,
  ): Promise<ListenableFuture> {
    const query: ListenableFuture = body;
    const detectedResponse = await this.dialogflowService
      .detectIntent(query.text, 'en-US')
      .then(result => {
        return result;
      }).catch((error) => {
        console.log(error);
      });
    await this.avatarService.speak().then(() => {
      Logger.log('Avatars speaks');
    });
    await this.voiceService
      .talk('en-US', detectedResponse, false)
      .then(value => {
        Logger.log('Voice talks');
      });
    await this.avatarService.follow().then(() => {
      Logger.log('Avatars follows');
    });
    return query;
  }
}
