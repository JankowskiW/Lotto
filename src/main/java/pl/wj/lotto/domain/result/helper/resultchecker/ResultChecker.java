package pl.wj.lotto.domain.result.helper.resultchecker;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.model.dto.ResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.ArrayList;
import java.util.List;

public class ResultChecker implements ResultCheckerPort {
    @Override
    public List<ResultDto> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> ticketNumbers) {
        List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
        List<List<Integer>> extraNumbers = ticketNumbers.stream().map(PlayerNumbersDto::extraNumbers).toList();
        return switch(drawResultDto.type()) {
            case LOTTO -> getResultForLottoDraw(drawResultDto.numbers().mainNumbers(), mainNumbers);
            case Q600 -> getResultForQuick600Draw();
            case EJP -> getResultForEurojackpotDraw();
            case KENO -> getResultForKenoDraw();
        };
    }

    private List<ResultDto> getResultForLottoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        int mainNumbersAmount = winningNumbers.size();
        int levelsAmount = 4;
        List<ResultDto> resultDtos = new ArrayList<>();
        for (int i = 1; i <= levelsAmount; i++)
            resultDtos.add(new ResultDto(i, 0));

        for(List<Integer> numbers : mainNumbers) {
            long correctNumbersAmount = numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount < levelsAmount) continue;
            resultDtos.get((int) correctNumbersAmount - mainNumbersAmount + 1);
            // TODO: increment winners amount
        }
        return resultDtos;
    }

    private List<ResultDto> getResultForQuick600Draw() {
        return List.of();
    }

    private List<ResultDto> getResultForEurojackpotDraw() {
        return List.of();
    }

    private List<ResultDto> getResultForKenoDraw() {
        return List.of();
    }
}
