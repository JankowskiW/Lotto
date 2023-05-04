package pl.wj.lotto.domain.draw.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.service.DrawService;

import java.util.List;

@RequiredArgsConstructor
public class DrawServiceAdapter implements DrawServicePort {
    private final DrawService drawService;

    @Override
    public List<DrawResponseDto> getGameTypeDraws(int gameTypeId) {
        return drawService.getGameTypeDraws(gameTypeId);
    }

    @Override
    public DrawResponseDto addDraw(DrawRequestDto drawRequestDto) {
        // TODO: that method should be called from scheduler
        return drawService.addDraw(drawRequestDto);
    }

    @Override
    public DrawResponseDto getDraw(String id) {
        return drawService.getDraw(id);
    }

    @Override
    public DrawResultDto getDrawResult(String drawId) {
        return drawService.getDrawResult(drawId);
    }

}
