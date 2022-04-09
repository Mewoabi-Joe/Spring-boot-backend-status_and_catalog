package reseau.project.status.statusPreview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusPreviewResponse {
    private int userNumber;
    private int numberOfStatus;
    private String lastImageThumb;
    private String lastStatusTime;
}