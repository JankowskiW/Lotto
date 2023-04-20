package pl.wj.lotto.infrastructure.application.rest.drawing;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;

import java.util.List;

@RestController
@RequestMapping("/drawings")
@RequiredArgsConstructor
public class DrawingController {
    private final DrawingServicePort drawingServicePort;

    @GetMapping("/{drawingTypeId}")
    public List<Drawing> getDrawings(@PathVariable int drawingTypeId) {
        return drawingServicePort.getDrawings(drawingTypeId);
    }
}
