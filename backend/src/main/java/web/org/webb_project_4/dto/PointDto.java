package web.org.webb_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.org.webb_project_4.model.Points;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {
    private Double x;
    private Double y;
    private Double r;
    private boolean result;

    public static List<PointDto> addToPointDtoList(List<Points> pointsList){
        return pointsList.stream()
                .map(results -> PointDto.builder()
                        .x(results.getX())
                        .y(results.getY())
                        .r(results.getR())
                        .result(results.isResult())
                        .build())
                .collect(Collectors.toList());
    }
}
