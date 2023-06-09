package pl.wj.lotto.domain.result.helper.resultchecker;

import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.result.helper.resultchecker.port.in.ResultCheckerPort;
import pl.wj.lotto.domain.result.model.dto.TicketResultDto;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.PlayerNumbersDto;
import pl.wj.lotto.infrastructure.application.exception.definition.DrawResultCalculateException;

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
    public Map<String, Integer> getResultsForDraw(DrawWinningNumbersDto drawWinningNumbersDto, List<PlayerNumbersDto> ticketNumbers) {
        Map<String, Integer> levelsWinnersAmount = new HashMap<>();
        List<Integer> mainWinningNumbers = drawWinningNumbersDto.numbers().mainNumbers();
        switch(drawWinningNumbersDto.type()) {
            case LOTTO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForLottoDraw(mainWinningNumbers, mainNumbers);
            }
            case Q600 -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForQuick600Draw(mainWinningNumbers, mainNumbers);
            }
            case EJP -> {
                List<Integer> extraWinningNumbers = drawWinningNumbersDto.numbers().extraNumbers();
                levelsWinnersAmount = getResultForEurojackpotDraw(mainWinningNumbers, extraWinningNumbers, ticketNumbers);
            }
            case KENO -> {
                List<List<Integer>> mainNumbers = ticketNumbers.stream().map(PlayerNumbersDto::mainNumbers).toList();
                levelsWinnersAmount = getResultForKenoDraw(mainWinningNumbers, mainNumbers);
            }
        }
        return levelsWinnersAmount;
    }

    @Override
    public TicketResultsDetailsDto getResultsForTicket(Ticket ticket, List<Draw> ticketDraws) {
        List<TicketResultDto> ticketResultDtos = new ArrayList<>();
        for(Draw draw : ticketDraws) {
            Map<String, Integer> levelsWinnersAmount = new HashMap<>();
            Numbers winningNumbers = draw.getNumbers();
            switch(ticket.getGameType()) {
                case LOTTO -> {
                    List<Integer> mainNumbers = ticket.getNumbers().mainNumbers();
                    levelsWinnersAmount = getResultForLottoDraw(winningNumbers.mainNumbers(), List.of(mainNumbers));
                }
                case Q600 -> {
                    List<Integer> mainNumbers = ticket.getNumbers().mainNumbers();
                    levelsWinnersAmount = getResultForQuick600Draw(winningNumbers.mainNumbers(), List.of(mainNumbers));
                }
                case EJP -> levelsWinnersAmount = getResultForEurojackpotDraw(
                        winningNumbers.mainNumbers(), winningNumbers.extraNumbers(),
                        List.of(TicketMapper.toPlayerNumbersDto(ticket)));
                case KENO -> {
                    List<Integer> mainNumbers = ticket.getNumbers().mainNumbers();
                    levelsWinnersAmount = getResultForKenoDraw(winningNumbers.mainNumbers(), List.of(mainNumbers));
                }
            }
            TicketResultDto ticketResultDto = TicketResultDto.builder()
                    .drawId(draw.getId())
                    .level(levelsWinnersAmount.entrySet().stream().filter(e -> e.getValue() > 0).map(Map.Entry::getKey).findFirst().orElse("0"))
                    .winningNumbers(winningNumbers)
                    .ticketNumbers(ticket.getNumbers())
                    .prize(0.0) // TODO: think how to calculate prize
                    .build();
            ticketResultDtos.add(ticketResultDto);
        }
        return TicketResultsDetailsDto.builder()
                .ticketId(ticket.getId())
                .results(ticketResultDtos)
                .totalPrize(ticketResultDtos.stream().mapToDouble(TicketResultDto::prize).sum())
                .build();
    }

    private Map<String, Integer> getResultForLottoDraw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        Map<String, Integer> results = new HashMap<>();
        for (int i = 1; i <= LOTTO_LEVELS_AMOUNT; i++)
            results.put(String.valueOf(i), 0);

        for(List<Integer> numbers : mainNumbers) {
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount <= winningNumbers.size() - LOTTO_LEVELS_AMOUNT) continue;
            String level = String.valueOf(winningNumbers.size() - correctNumbersAmount + 1);
            if (!results.containsKey(level)) throw new DrawResultCalculateException("Cannot find LOTTO " + level + " level");
            int newWinnersAmount = results.get(level) + 1;
            results.put(level, newWinnersAmount);
        }
        return results;
    }

    private Map<String, Integer> getResultForQuick600Draw(List<Integer> winningNumbers, List<List<Integer>> mainNumbers) {
        Map<String, Integer> results = new HashMap<>();
        for (int i = 1; i <= Q600_LEVELS_AMOUNT; i++)
            results.put(String.valueOf(i), 0);

        for(List<Integer> numbers : mainNumbers) {
            int correctNumbersAmount = (int) numbers.stream().filter(winningNumbers::contains).count();
            if (correctNumbersAmount <= winningNumbers.size() - Q600_LEVELS_AMOUNT) continue;
            String level = String.valueOf(winningNumbers.size() - correctNumbersAmount + 1);
            if (!results.containsKey(level)) throw new DrawResultCalculateException("Cannot find Q600 " + level + " level");
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
                default -> mainNumbersPossibleLevels = new ArrayList<>();
            }
            switch(correctExtraNumbersAmount) {
                case 2 -> extraNumbersPossibleLevels = List.of(1, 4, 6, 8, 11);
                case 1 -> extraNumbersPossibleLevels = List.of(2, 5, 9, 12);
                default -> extraNumbersPossibleLevels = List.of(3, 7, 10);
            }
            mainNumbersPossibleLevels.retainAll(extraNumbersPossibleLevels);
            if (mainNumbersPossibleLevels.size() == 0) continue;
            if (mainNumbersPossibleLevels.size() > 1) throw new DrawResultCalculateException("Unexpected value during calculating Eurojackpot draw results");
            String level = String.valueOf(mainNumbersPossibleLevels.get(0));
            if (!results.containsKey(level)) throw new DrawResultCalculateException("Cannot find EJP " + level + " level");
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
            if (!results.containsKey(level)) throw new DrawResultCalculateException("Cannot find KENO " + level + " level");
            int newWinnersAmount = results.get(level) + 1;
            results.put(level, newWinnersAmount);
        }

        return results;
    }
}
