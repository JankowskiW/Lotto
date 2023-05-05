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
        List<Integer> mainWinningNumbers = drawResultDto.numbers().mainNumbers();
        switch(drawResultDto.type()) {
            case LOTTO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForLottoDraw(mainWinningNumbers, mainNumbers);
            }
            case Q600 -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForQuick600Draw(mainWinningNumbers, mainNumbers);
            }
            case EJP -> {
                List<Integer> extraWinningNumbers = drawResultDto.numbers().extraNumbers();
                levelsWinnersAmount = getResultForEurojackpotDraw(mainWinningNumbers, extraWinningNumbers, ticketNumbers);
            }
            case KENO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForKenoDraw();
            }
        }
        return levelsWinnersAmount;
    }

    private Map<Integer, Integer> getResultForLottoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        return getResultForGamesWithSimpleRules(winningNumbers, mainNumbers, LOTTO_LEVELS_AMOUNT);
    }

    private Map<Integer, Integer> getResultForQuick600Draw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        return getResultForGamesWithSimpleRules(winningNumbers, mainNumbers, Q600_LEVELS_AMOUNT);
    }

    private Map<Integer, Integer> getResultForGamesWithSimpleRules(
            List<Integer> winningNumbers, List<List<Integer>> mainNumbers, Integer levelsAmount) {
        Map<Integer, Integer> levelsWinnersAmount = new HashMap<>();
        for (int i = 1; i <= levelsAmount; i++)
            levelsWinnersAmount.put(i, 0);

        for(List<Integer> numbers : mainNumbers) {
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount <= winningNumbers.size() - levelsAmount) continue;
            int level = winningNumbers.size() - correctNumbersAmount + 1;
            int newWinnersAmount = levelsWinnersAmount.get(level) + 1;
            levelsWinnersAmount.put(level, newWinnersAmount);
        }
        return levelsWinnersAmount;
    }

    private Map<Integer, Integer> getResultForEurojackpotDraw(
            List<Integer> mainWinningNumbers, List<Integer> extraWinningNumbers, List<PlayerNumbersDto> ticketNumbers) {
        Map<Integer, Integer> levelsWinnersAmount = new HashMap<>();
        List<Integer> mainNumbersPossibleLevels;
        List<Integer> extraNumbersPossibleLevels;
        for (int i = 1; i <= EJP_LEVELS_AMOUNT; i++)
            levelsWinnersAmount.put(i, 0);

        for(PlayerNumbersDto numbers : ticketNumbers) {
            int correctMainNumbersAmount = (int) numbers.mainNumbers().stream().filter(mainWinningNumbers::contains).count();
            int correctExtraNumbersAmount = (int) numbers.extraNumbers().stream().filter(extraWinningNumbers::contains).count();
            switch(correctMainNumbersAmount) {
                case 5 -> mainNumbersPossibleLevels = List.of(1, 2, 3);
                case 4 -> mainNumbersPossibleLevels = List.of(4, 5, 7);
                case 3 -> mainNumbersPossibleLevels = List.of(6, 9, 10);
                case 2 -> mainNumbersPossibleLevels = List.of(8, 12);
                case 1 -> mainNumbersPossibleLevels = List.of(11);
                default -> mainNumbersPossibleLevels = List.of();
            }
            switch(correctExtraNumbersAmount) {
                case 2 -> extraNumbersPossibleLevels = List.of(1, 4, 6, 8, 11);
                case 1 -> extraNumbersPossibleLevels = List.of(2, 5, 9, 12);
                case 0 -> extraNumbersPossibleLevels = List.of(3, 7, 10);
                default -> extraNumbersPossibleLevels = List.of();
            }
            mainNumbersPossibleLevels.retainAll(extraNumbersPossibleLevels);
            if (mainNumbersPossibleLevels.size() == 0) continue;
            if (mainNumbersPossibleLevels.size() > 1) throw new RuntimeException("Unexpected value during calculating Eurojackpot draw results");
            int level = mainNumbersPossibleLevels.get(0);
            int newWinnersAmount = levelsWinnersAmount.get(level) + 1;
            levelsWinnersAmount.put(level, newWinnersAmount);
        }
        return levelsWinnersAmount;
    }

    private Map<Integer, Integer> getResultForKenoDraw() {
        return Map.of();
    }
}
