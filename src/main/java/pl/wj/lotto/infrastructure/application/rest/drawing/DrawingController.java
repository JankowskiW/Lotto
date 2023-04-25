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
    public List<DrawingResponseDto> getDrawingsByType(@RequestParam(name = "type") int typeId) {
        return drawingServicePort.getDrawingsByTypeId(typeId);
    }

    @PostMapping
    public DrawingResponseDto addDrawing(@RequestBody DrawingRequestDto drawingRequestDto) {
        // TODO: remove that endpoint, because user cannot create new drawing through http,
        // TODO: drawings should be created using scheduler
        // TODO: migrate that method to TicketController where user should can add new ticket using
        // TODO: NumbersTemplate and similar methods
        return drawingServicePort.addDrawing(drawingRequestDto);
    }

    @GetMapping("/{id}")
    public DrawingResponseDto getDrawingById(@PathVariable String id) {
        return drawingServicePort.getDrawingById(id);
    }
}
