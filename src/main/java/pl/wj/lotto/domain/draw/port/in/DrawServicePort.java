package pl.wj.lotto.domain.draw.port.in;

import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTime;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface DrawServicePort {
    List<DrawResponseDto> getDrawsByTypeId(int gameTypeId);

    DrawResponseDto addDraw(DrawRequestDto drawRequestDto);

    DrawResponseDto getDrawById(String id);
    LocalDateTime getNextDrawDateTime(DrawDateTime drawDateTime);
}
