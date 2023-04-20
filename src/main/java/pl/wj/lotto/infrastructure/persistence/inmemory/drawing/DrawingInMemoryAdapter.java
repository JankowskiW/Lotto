package pl.wj.lotto.infrastructure.persistence.inmemory.drawing;

import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrawingInMemoryAdapter implements DrawingRepositoryPort {
    @Override
    public List<Drawing> findAllByType(DrawingType type) {
        return Stream.of(
                new Drawing("1", List.of(1,2,3,4,5), List.of(1,2), DrawingType.EJP, LocalDate.now(), LocalTime.now()),
                new Drawing("2", List.of(1,2,3,4,5,7), null, DrawingType.LOTTO, LocalDate.now(), LocalTime.now()),
                new Drawing("3", List.of(1,2,3,4,5,8), null, DrawingType.LOTTO, LocalDate.now(), LocalTime.now()),
                new Drawing("4", List.of(1,2,3,4,5,9), null, DrawingType.KENO, LocalDate.now(), LocalTime.now()),
                new Drawing("5", List.of(1,2,3,4,5,10), null, DrawingType.KENO, LocalDate.now(), LocalTime.now()),
                new Drawing("6", List.of(1,2,3,4,5,11), null, DrawingType.KENO, LocalDate.now(), LocalTime.now()),
                new Drawing("7", List.of(1,2,3,4,5,12), null, DrawingType.Q600, LocalDate.now(), LocalTime.now())
        ).filter(d -> d.getType().equals(type)).collect(Collectors.toList());
    }
}
