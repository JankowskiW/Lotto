package pl.wj.lotto.domain.drawing.port.in;

import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;

import java.util.List;

public interface DrawingServicePort {
    List<DrawingResponseDto> getDrawingsByTypeId(int typeId);

    DrawingResponseDto addDrawing(DrawingRequestDto drawingRequestDto);

    DrawingResponseDto getDrawingById(String id);
}
