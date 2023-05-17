package pl.wj.lotto.infrastructure.application.rest.draw;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.in.DrawServicePort;

@RestController
@RequestMapping("/draws")
@RequiredArgsConstructor
public class DrawController {
    private final DrawServicePort drawServicePort;

    @GetMapping
    public Page<DrawResponseDto> getGameTypeDraws(@RequestParam(name = "gameType") int gameTypeId,
                                                  @RequestParam int pageNumber, @RequestParam int pageSize) {
        return drawServicePort.getGameTypeDraws(gameTypeId, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public DrawResponseDto getDraw(@PathVariable String id) {
        return drawServicePort.getDraw(id);
    }

}
