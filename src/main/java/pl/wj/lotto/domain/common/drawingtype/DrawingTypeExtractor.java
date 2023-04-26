package pl.wj.lotto.domain.common.drawingtype;

import java.util.stream.Stream;

public class DrawingTypeExtractor {
    public static DrawingType getDrawingTypeById(int id) {
        return Stream.of(DrawingType.values())
                .filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public static DrawingType getDrawingTypeByName(String name) {
        return Stream.of(DrawingType.values())
                .filter(dt -> dt.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
