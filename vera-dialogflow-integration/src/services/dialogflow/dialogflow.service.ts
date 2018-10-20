import { Injectable, Logger } from '@nestjs/common';
import { SessionsClient } from 'dialogflow';

@Injectable()
export class DialogflowService {
  private readonly projectId: string = 'vera-b884c';
  private readonly sessionId: string = 'quickstart-session-id';
  private readonly sessionClient = new SessionsClient();
  constructor() {}

  public detectIntent(query: string, languageCode: string): Promise<any> {
    const sessionPath = this.sessionClient.sessionPath(
      this.projectId,
      this.sessionId,
    );
    const request = {
      session: sessionPath,
      queryInput: {
        text: {
          text: query,
          languageCode: languageCode,
        },
      },
    };

    // Send request and log result
    return this.sessionClient
      .detectIntent(request)
      .then(responses => {
        Logger.log('Detected intent');
        const result = responses[0].queryResult;
        Logger.log(`  Query: ${result.queryText}`);
        Logger.log(`  Response: ${result.fulfillmentText}`);
        if (result.intent) {
          Logger.log(`  Intent: ${result.intent.displayName}`);
        } else {
          Logger.log(`  No intent matched.`);
        }
        return result;
      })
      .catch(err => {
        Logger.error('ERROR:', err);
        return null;
      });
  }
}
