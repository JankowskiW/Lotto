package pl.wj.lotto.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawdatetime.port.in.DrawDateTimeCheckerPort;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersGeneratorPort;
import pl.wj.lotto.domain.common.numbers.port.in.NumbersValidatorPort;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.Ticket;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TicketService {
    private final TicketRepositoryPort ticketRepositoryPort;
    private final NotificationPort notificationPort;
    private final DrawDateTimeCheckerPort drawDateTimeCheckerPort;
    private final NumbersGeneratorPort numbersGeneratorPort;
    private final NumbersValidatorPort numbersValidatorPort;

    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket = TicketMapper.toTicket(ticketRequestDto);
        if (ticketRequestDto.numbersAutogenerated())
            ticket.setNumbers(numbersGeneratorPort.generate(ticket.getGameType()));
        if (!numbersValidatorPort.validate(ticket.getNumbers())) {
            throw new RuntimeException("Given numbers are invalid");
        }

        ticket = ticketRepositoryPort.save(ticket);
        if (ticket.getUserId() != null && !ticket.getUserId().isBlank()) {
            notificationPort.send("email@email.com", "Message");
        } else {
            ticket.setUserId("");
        }
        TicketResponseDto ticketResponseDto = TicketMapper.toTicketResponseDto(ticket);
        LocalDateTime nextDrawDateTime = drawDateTimeCheckerPort.getNextDrawDateTime(ticket.getNumbers().drawDateTime());
        return ticketResponseDto.withNextDrawDateTime(nextDrawDateTime);
    }

    public List<TicketResponseDto> getTicketsByUserId(String userId) {
        List<Ticket> tickets = ticketRepositoryPort.getByUserId(userId);
        // TODO: Create method which gets next draw time for specific draw type somewhere and call it here
        return TicketMapper.toTicketResponseDtos(tickets);
    }
}
