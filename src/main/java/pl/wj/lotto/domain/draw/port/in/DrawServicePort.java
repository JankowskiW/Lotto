package pl.wj.lotto.domain.draw.port.in;

import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.model.vo.DrawGameTypeAndDateTimeVo;

import java.util.List;

public interface DrawServicePort {
    List<DrawResponseDto> getGameTypeDraws(int gameTypeId);

    DrawResponseDto addDraw(DrawRequestDto drawRequestDto);

    DrawResponseDto getDraw(String id);

    DrawGameTypeAndDateTimeVo getDrawGameTypeAndDateTime(String drawId);
}
