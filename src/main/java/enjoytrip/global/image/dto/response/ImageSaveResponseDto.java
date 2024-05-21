package enjoytrip.global.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageSaveResponseDto {

    private String url;

    public void addUrl(String url) {
        this.url = url;
    }
}
