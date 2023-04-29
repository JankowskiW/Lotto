package pl.wj.lotto.infrastructure.application.rest.draw;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;

import java.util.List;

@RestController
@RequestMapping("/draws")
@RequiredArgsConstructor
public class DrawController {
    private final DrawServicePort drawServicePort;

    @GetMapping
    public List<DrawResponseDto> getDrawsByType(@RequestParam(name = "gameType") int gameTypeId) {
        return drawServicePort.getDrawsByTypeId(gameTypeId);
    }

    @GetMapping("/{id}")
    public DrawResponseDto getDrawById(@PathVariable String id) {
        return drawServicePort.getDrawById(id);
    }


    @PostMapping
    public DrawResponseDto addDraw(@RequestBody DrawRequestDto drawRequestDto) {
        return drawServicePort.addDraw(drawRequestDto);
    }
}
