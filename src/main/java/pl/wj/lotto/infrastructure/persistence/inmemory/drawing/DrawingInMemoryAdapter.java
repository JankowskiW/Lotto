package pl.wj.lotto.infrastructure.persistence.inmemory.drawing;

import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DrawingInMemoryAdapter implements DrawingRepositoryPort {

    private final Map<String, Drawing> drawingsTable = new ConcurrentHashMap<>();
    @Override
    public List<Drawing> findAllByType(DrawingType type) {
        return drawingsTable.values().stream().filter(d -> d.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Drawing save(Drawing drawing) {
        String id = drawing.getId() == null ? UUID.randomUUID().toString() : drawing.getId();
        drawing.setId(id);
        drawingsTable.put(id, drawing);
        return drawing;
    }

    @Override
    public Optional<Drawing> findById(String id) {
        return Optional.of(drawingsTable.get(id));
    }
}
