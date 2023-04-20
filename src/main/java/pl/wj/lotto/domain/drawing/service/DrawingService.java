package pl.wj.lotto.domain.drawing.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.drawing.mapper.DrawingMapper;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class DrawingService {
    private final DrawingRepositoryPort drawingRepositoryPort;


    public List<DrawingResponseDto> getDrawingsByType(int drawingTypeId) {
        List<Drawing> drawings = drawingRepositoryPort.findAllByType(getDrawingTypeById(drawingTypeId));
        return DrawingMapper.toDrawingResponseDtos(drawings);
    }

    public DrawingResponseDto addDrawing(DrawingRequestDto drawingRequestDto) {
        Drawing drawing = DrawingMapper.toDrawing(drawingRequestDto);
        drawing = drawingRepositoryPort.save(drawing);
        return DrawingMapper.toDrawingResponseDto(drawing);
    }

    public DrawingResponseDto getDrawingById(String id) {
        Drawing drawing = drawingRepositoryPort.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return DrawingMapper.toDrawingResponseDto(drawing);
    }


    private DrawingType getDrawingTypeById(int id) {
        return Arrays.stream(DrawingType.values()).filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid parameter"));
    }
}
