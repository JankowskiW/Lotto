package pl.wj.lotto.domain.result.helper.resultchecker;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultChecker implements ResultCheckerPort {
    private static final int LOTTO_LEVELS_AMOUNT = 4;
    private static final int Q600_LEVELS_AMOUNT = 5;
    private static final int EJP_LEVELS_AMOUNT = 12;

    @Override
    public Map<Integer, Integer> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> ticketNumbers) {
        Map<Integer, Integer> levelsWinnersAmount = new HashMap<>();
        switch(drawResultDto.type()) {
            case LOTTO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForLottoDraw(drawResultDto.numbers().mainNumbers(), mainNumbers);
            }
            case Q600 -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForQuick600Draw();
            }
            case EJP -> {
                levelsWinnersAmount = getResultForEurojackpotDraw();
            }
            case KENO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForKenoDraw();
            }
        }
        return levelsWinnersAmount;
    }

    private Map<Integer, Integer> getResultForLottoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        Map<Integer, Integer> levelsWinnersAmount = new HashMap<>();
        for (int i = 1; i <= LOTTO_LEVELS_AMOUNT; i++)
            levelsWinnersAmount.put(i, 0);

        for(List<Integer> numbers : mainNumbers) {
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount <= winningNumbers.size() - LOTTO_LEVELS_AMOUNT) continue;
            int level = winningNumbers.size() - correctNumbersAmount + 1;
            int newWinnersAmount = levelsWinnersAmount.get(level) + 1;
            levelsWinnersAmount.put(level, newWinnersAmount);
        }
        return levelsWinnersAmount;
    }

    private Map<Integer, Integer> getResultForQuick600Draw() {
        return Map.of();
    }

    private Map<Integer, Integer> getResultForEurojackpotDraw() {
        return Map.of();
    }

    private Map<Integer, Integer> getResultForKenoDraw() {
        return Map.of();
    }
}
