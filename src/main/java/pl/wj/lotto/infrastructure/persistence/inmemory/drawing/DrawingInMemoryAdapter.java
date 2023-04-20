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

    private static final Map<String, Drawing> database;

    static {
        database = new ConcurrentHashMap<>();
    }

    @Override
    public List<Drawing> findAllByType(DrawingType type) {
        return database.values().stream().filter(d -> d.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Drawing save(Drawing drawing) {
        drawing.setId(UUID.randomUUID().toString());
        database.put(drawing.getId(), drawing);
        return drawing;
    }

    @Override
    public Optional<Drawing> findById(String id) {
        return Optional.of(database.get(id));
    }
}
