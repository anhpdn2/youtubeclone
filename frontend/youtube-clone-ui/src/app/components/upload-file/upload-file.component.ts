import { Component } from '@angular/core';
import {UploadFileService} from "../../service/upload-file.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {FileSystemFileEntry, NgxFileDropEntry} from "ngx-file-drop";
import {VideoService} from "../../service/video.service";

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent {
  public files: NgxFileDropEntry[] = [];
  fileUpload: boolean = false;
  fileEntry: FileSystemFileEntry | undefined;

  constructor(
    private videoService: VideoService
  ) {
  }

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {
          this.fileUpload = true;
          // Here you can access the real file
          console.log(droppedFile.relativePath, file);

          /**
           // You could upload it like this:
           const formData = new FormData()
           formData.append('logo', file, relativePath)

           // Headers
           const headers = new HttpHeaders({
            'security-token': 'mytoken'
          })

           this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
           .subscribe(data => {
            // Sanitized logo returned from backend
          })
           **/

        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any){
    console.log(event);
  }

  public fileLeave(event: any){
    console.log(event);
  }

  uploadVideo() {
    if (this.fileEntry !== undefined) {
      console.log(this.fileEntry);
      this.fileEntry.file(file => {
        this.videoService.uploadVideo(file).subscribe(() => {
          console.log("Video can upload successfully");
        })
      })

    }
  }
}
