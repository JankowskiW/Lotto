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
    public List<DrawResponseDto> getGameTypeDraws(@RequestParam(name = "gameType") int gameTypeId) {
        return drawServicePort.getGameTypeDraws(gameTypeId);
    }

    @GetMapping("/{id}")
    public DrawResponseDto getDraw(@PathVariable String id) {
        return drawServicePort.getDraw(id);
    }


    @PostMapping
    public DrawResponseDto addDraw(@RequestBody DrawRequestDto drawRequestDto) {
        return drawServicePort.addDraw(drawRequestDto);
    }
}
