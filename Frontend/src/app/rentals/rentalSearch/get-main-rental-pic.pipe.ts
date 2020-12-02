import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { DefaultProfilePic } from '@app/_models/defaultProfilePic';

@Pipe({
  name: 'getMainRentalPic',
  pure: true
})
export class GetMainRentalPicPipe implements PipeTransform {

  constructor(protected sanitizer: DomSanitizer) {
  }

  transform(value: any): SafeHtml {
    if (value == null) {
      value = DefaultProfilePic;
    }
    return this.sanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64, ' + value);
  }

}
