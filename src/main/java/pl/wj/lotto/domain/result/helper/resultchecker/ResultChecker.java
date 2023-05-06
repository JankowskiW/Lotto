package pl.wj.lotto.domain.result.helper.resultchecker;

import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultChecker implements ResultCheckerPort {
    private static final int LOTTO_LEVELS_AMOUNT = 4;
    private static final int Q600_LEVELS_AMOUNT = 5;
    private static final int EJP_LEVELS_AMOUNT = 12;
    private static final int KENO_MAX_NUMBERS_AMOUNT = 10;

    @Override
    public Map<String, Integer> getResultsForDraw(DrawResultDto drawResultDto, List<PlayerNumbersDto> ticketNumbers) {
        Map<String, Integer> levelsWinnersAmount = new HashMap<>();
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
                levelsWinnersAmount = getResultForKenoDraw(mainWinningNumbers, mainNumbers);
            }
        }
        return levelsWinnersAmount;
    }

    private Map<String, Integer> getResultForLottoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        return getResultForGamesWithSimpleRules(winningNumbers, mainNumbers, LOTTO_LEVELS_AMOUNT);
    }

    private Map<String, Integer> getResultForQuick600Draw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        return getResultForGamesWithSimpleRules(winningNumbers, mainNumbers, Q600_LEVELS_AMOUNT);
    }

    private Map<String, Integer> getResultForGamesWithSimpleRules(
            List<Integer> winningNumbers, List<List<Integer>> mainNumbers, Integer levelsAmount) {
        Map<String, Integer> results = new HashMap<>();
        for (int i = 1; i <= levelsAmount; i++)
            results.put(String.valueOf(i), 0);

        for(List<Integer> numbers : mainNumbers) {
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount <= winningNumbers.size() - levelsAmount) continue;
            String level = String.valueOf(winningNumbers.size() - correctNumbersAmount + 1);
            int newWinnersAmount = results.get(level) + 1;
            results.put(level, newWinnersAmount);
        }
        return results;
    }

    private Map<String, Integer> getResultForEurojackpotDraw(
            List<Integer> mainWinningNumbers, List<Integer> extraWinningNumbers, List<PlayerNumbersDto> ticketNumbers) {
        Map<String, Integer> results = new HashMap<>();
        List<Integer> mainNumbersPossibleLevels;
        List<Integer> extraNumbersPossibleLevels;
        for (int i = 1; i <= EJP_LEVELS_AMOUNT; i++)
            results.put(String.valueOf(i), 0);

        for(PlayerNumbersDto numbers : ticketNumbers) {
            int correctMainNumbersAmount = (int) numbers.mainNumbers().stream().filter(mainWinningNumbers::contains).count();
            int correctExtraNumbersAmount = (int) numbers.extraNumbers().stream().filter(extraWinningNumbers::contains).count();
            switch(correctMainNumbersAmount) {
                case 5 -> mainNumbersPossibleLevels = new ArrayList<>(List.of(1, 2, 3));
                case 4 -> mainNumbersPossibleLevels = new ArrayList<>(List.of(4, 5, 7));
                case 3 -> mainNumbersPossibleLevels = new ArrayList<>(List.of(6, 9, 10));
                case 2 -> mainNumbersPossibleLevels = new ArrayList<>(List.of(8, 12));
                case 1 -> mainNumbersPossibleLevels = new ArrayList<>(List.of(11));
                default -> mainNumbersPossibleLevels = new ArrayList<>(List.of());
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
            String level = String.valueOf(mainNumbersPossibleLevels.get(0));
            int newWinnersAmount = results.get(level) + 1;
            results.put(level, newWinnersAmount);
        }
        return results;
    }

    private Map<String, Integer> getResultForKenoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        Map<String, Integer> results = new HashMap<>();
        for (int i = 1; i <= KENO_MAX_NUMBERS_AMOUNT; i++)
            for (int j = 0; j <= i; j++)
                results.put(i + ";" + j, 0);

        for(List<Integer> numbers : mainNumbers) {
            int selectedNumbersAmount = numbers.size();
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            String level = selectedNumbersAmount + ";" + correctNumbersAmount;
            int newWinnersAmount = results.get(level) + 1;
            results.put(level, newWinnersAmount);
        }

        return results;
    }
}
