package backend.useraccess.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ChartDataDto {
    String name;
    List<Long> data;
}
