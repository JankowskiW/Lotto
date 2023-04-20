package pl.wj.lotto.domain.drawing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Drawing {
    String id;
    List<Integer> mainNumbers;
    List<Integer> extraNumbers;
    DrawingType type;
    LocalDate drawingDate;
    LocalTime drawingTime;

}
