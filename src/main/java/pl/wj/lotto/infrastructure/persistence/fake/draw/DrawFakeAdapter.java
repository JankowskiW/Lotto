package pl.wj.lotto.infrastructure.persistence.fake.draw;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DrawFakeAdapter implements DrawRepositoryPort {
    private final Map<String, Draw> drawsTable = new ConcurrentHashMap<>();
    public List<Draw> findAllByType(GameType type) {
        return drawsTable.values().stream().filter(d -> d.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Draw save(Draw draw) {
        String id = draw.getId() == null ? UUID.randomUUID().toString() : draw.getId();
        draw.setId(id);
        drawsTable.put(id, draw);
        return draw;
    }

    @Override
    public Optional<Draw> findById(String id) {
        return Optional.of(drawsTable.get(id));
    }

    @Override
    public Optional<DrawResultDto> findDrawResultById(String drawId) {
        return Optional.of(DrawMapper.toDrawResultDto(drawsTable.get(drawId)));
    }

    @Override
    public List<Draw> findAllByTypeAndDrawDateTime(GameType gameType, LocalDateTime generationDateTime, LocalDateTime lastDrawDateTime) {
        return null;
    }
}
