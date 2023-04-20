package pl.wj.lotto.infrastructure.application.rest.drawing;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;

import java.util.List;

@RestController
@RequestMapping("/drawings")
@RequiredArgsConstructor
public class DrawingController {
    private final DrawingServicePort drawingServicePort;

    @GetMapping
    public List<DrawingResponseDto> getDrawingsByType(@RequestParam(name = "type") int drawingTypeId) {
        return drawingServicePort.getDrawingsByType(drawingTypeId);
    }

    @PostMapping
    public DrawingResponseDto addDrawing(@RequestBody DrawingRequestDto drawingRequestDto) {
        return drawingServicePort.addDrawing(drawingRequestDto);
    }

    @GetMapping("/{id}")
    public DrawingResponseDto getDrawingById(@PathVariable String id) {
        return drawingServicePort.getDrawingById(id);
    }
}
