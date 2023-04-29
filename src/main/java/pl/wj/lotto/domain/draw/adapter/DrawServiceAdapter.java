package pl.wj.lotto.domain.draw.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawtime.model.DrawTime;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;
import pl.wj.lotto.domain.draw.service.DrawService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class DrawServiceAdapter implements DrawServicePort {
    private final DrawService drawService;

    @Override
    public List<DrawResponseDto> getDrawsByTypeId(int gameTypeId) {
        return drawService.getDrawsByTypeId(gameTypeId);
    }

    @Override
    public DrawResponseDto addDraw(DrawRequestDto drawRequestDto) {
        // TODO: that method should be called from scheduler
        return drawService.addDraw(drawRequestDto);
    }

    @Override
    public DrawResponseDto getDrawById(String id) {
        return drawService.getDrawById(id);
    }

    @Override
    public LocalDateTime getNextDrawTime(DrawTime drawTime) {
        return drawService.getNextDrawTime(drawTime);
    }

}
