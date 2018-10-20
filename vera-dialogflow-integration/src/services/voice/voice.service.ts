import { Injectable, HttpService } from '@nestjs/common';

@Injectable()
export class VoiceService {
  constructor(private readonly httpService: HttpService) {}

  talk(locale: string, text: any, isMasculine: boolean): Promise<any> {
    const config = {
      locale: locale,
      text: text.fulfillmentText,
      masculine: isMasculine,
    };
    console.info(config);
    return this.httpService
      .post('http://localhost:8080/talk/', config)
      .toPromise();
  }
}
