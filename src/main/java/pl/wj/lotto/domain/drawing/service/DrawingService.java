package pl.wj.lotto.domain.drawing.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.drawingtype.DrawingTypeExtractor;
import pl.wj.lotto.domain.drawing.mapper.DrawingMapper;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class DrawingService {
    private final DrawingRepositoryPort drawingRepositoryPort;


    public List<DrawingResponseDto> getDrawingsByTypeId(int typeId) {
        DrawingType type = DrawingTypeExtractor.getDrawingTypeById(typeId);
        List<Drawing> drawings = drawingRepositoryPort.findAllByType(type);
        return DrawingMapper.toDrawingResponseDtos(drawings);
    }

    public DrawingResponseDto addDrawing(DrawingRequestDto drawingRequestDto) {
        Drawing drawing = DrawingMapper.toDrawing(drawingRequestDto);
        drawing = drawingRepositoryPort.save(drawing);
        return DrawingMapper.toDrawingResponseDto(drawing);
    }

    public DrawingResponseDto getDrawingById(String id) {
        Drawing drawing = drawingRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Drawing not found"));
        return DrawingMapper.toDrawingResponseDto(drawing);
    }

    public LocalDateTime getNextDrawingTime(DrawingType type) {
        switch(type) {
            case LOTTO -> LocalDateTime.now();
            case Q600 -> LocalDateTime.now();
            case EJP -> LocalDateTime.now();
            case KENO -> LocalDateTime.now();
        }
        return null;
    }
}
