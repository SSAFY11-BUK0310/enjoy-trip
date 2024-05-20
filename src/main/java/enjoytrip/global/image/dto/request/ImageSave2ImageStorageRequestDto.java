package enjoytrip.global.image.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageSave2ImageStorageRequestDto {

    private String originalName;
    private String mimeType;
    private String base64File;
    private String directoryUUID;
}
