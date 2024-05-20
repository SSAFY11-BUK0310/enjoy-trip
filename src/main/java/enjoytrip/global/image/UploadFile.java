package enjoytrip.global.image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String uploadFileUUID;

    @Builder
    public UploadFile(String uploadFileName, String uploadFileUUID) {
        this.uploadFileName = uploadFileName;
        this.uploadFileUUID = uploadFileUUID;
    }
}
