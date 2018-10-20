import { Injectable, HttpService } from '@nestjs/common';
import { Observable } from 'rxjs';
import { AxiosResponse } from 'axios';

@Injectable()
export class AvatarService {
  constructor(private readonly httpService: HttpService) {}

  idle(): Promise<AxiosResponse<void>> {
    return this.httpService.get('http://localhost:8079/avatar/idle/').toPromise();
  }

  follow(): Promise<AxiosResponse<void>> {
    return this.httpService.get('http://localhost:8079/avatar/follow/').toPromise();
  }

  speak(): Promise<AxiosResponse<void>> {
    return this.httpService.get('http://localhost:8079/avatar/speak/').toPromise();
  }
}
