package web.org.webb_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointResponse {
    private String localTime;
    private long executionTime;
    private boolean result;
}
