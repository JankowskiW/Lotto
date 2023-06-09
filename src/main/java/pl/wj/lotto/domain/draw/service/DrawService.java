package pl.wj.lotto.domain.draw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeParser;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.infrastructure.application.exception.definition.ResourceNotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DrawService {
    private final Clock clock;
    private final DrawRepositoryPort drawRepositoryPort;
    private final NumbersGeneratorPort numbersGeneratorPort;


    public Page<DrawResponseDto> getGameTypeDraws(int gameTypeId, int pageNumber, int pageSize) {
        GameType type = GameTypeParser.getGameTypeById(gameTypeId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("drawDateTime").descending());
        Page<Draw> drawsPage = drawRepositoryPort.findAllByType(type, pageable);
        return DrawMapper.toDrawResponseDtosPage(drawsPage);
    }

    public DrawResponseDto addDraw(GameType gameType) {
        Draw draw = Draw.builder()
                .type(gameType)
                .drawDateTime(LocalDateTime.now(clock))
                .numbers(numbersGeneratorPort.generate(gameType,false))
                .build();
        draw = drawRepositoryPort.save(draw);
        return DrawMapper.toDrawResponseDto(draw);
    }

    public DrawResponseDto getDraw(String id) {
        Draw draw = drawRepositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Draw not found"));
        return DrawMapper.toDrawResponseDto(draw);
    }

    public DrawWinningNumbersDto getDrawWinningNumbers(String id) {
        return drawRepositoryPort.findDrawWinningNumbersById(id).orElseThrow(() -> new ResourceNotFoundException("Draw not found"));
    }

    public List<Draw> getDrawsForTicket(Ticket ticket) {
        List<Draw> draws = drawRepositoryPort.findByTypeAndDrawDateTimeBetween(
                ticket.getGameType(), ticket.getGenerationDateTime(), ticket.getLastDrawDateTime());
        return draws == null ? new ArrayList<>() : draws;
    }
}
