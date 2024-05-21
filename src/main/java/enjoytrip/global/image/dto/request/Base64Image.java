package enjoytrip.global.image.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Base64Image {

    private String originalName;
    private String base64File;
    private String extension;

    @Builder
    public Base64Image(String originalName, String base64File, String extension) {
        this.originalName = originalName;
        this.base64File = base64File;
        this.extension = extension;
    }
}
