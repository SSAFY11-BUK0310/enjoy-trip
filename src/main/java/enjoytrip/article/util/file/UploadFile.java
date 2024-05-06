package enjoytrip.article.util.file;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFile {
    private String uploadFileName;
    private String uploadFileUUID;

    public UploadFile(String uploadFileName, String uploadFileUUID) {
        this.uploadFileName = uploadFileName;
        this.uploadFileUUID = uploadFileUUID;
    }
}
