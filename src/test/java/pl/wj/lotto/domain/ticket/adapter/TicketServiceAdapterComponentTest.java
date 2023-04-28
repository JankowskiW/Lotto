package pl.wj.lotto.domain.ticket.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.notification.NotificationPort;
import pl.wj.lotto.domain.common.numbersgenerator.NumbersGeneratorPort;
import pl.wj.lotto.domain.drawing.adapter.DrawingServiceAdapter;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;
import pl.wj.lotto.domain.drawing.service.DrawingService;
import pl.wj.lotto.domain.ticket.mapper.TicketMapper;
import pl.wj.lotto.domain.ticket.model.dto.TicketRequestDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.ticket.port.out.TicketRepositoryPort;
import pl.wj.lotto.domain.ticket.service.TicketService;
import pl.wj.lotto.infrastructure.notification.inmemory.email.EmailNotificationInMemoryAdapter;
import pl.wj.lotto.infrastructure.numbergenerator.inmemory.NumbersGeneratorInMemoryAdapter;
import pl.wj.lotto.infrastructure.persistence.inmemory.drawing.DrawingInMemoryAdapter;
import pl.wj.lotto.infrastructure.persistence.inmemory.ticket.TicketInMemoryAdapter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TicketServiceAdapterComponentTest {
    private NotificationPort notificationPort;
    private NumbersGeneratorPort numbersGeneratorPort;
    private TicketRepositoryPort ticketRepositoryPort;
    private DrawingServicePort drawingServicePort;
    private TicketServiceAdapter ticketServiceAdapter;

    @BeforeEach
    void setUp() {
        notificationPort = new EmailNotificationInMemoryAdapter();
        ticketRepositoryPort = new TicketInMemoryAdapter();
        numbersGeneratorPort = new NumbersGeneratorInMemoryAdapter();
        DrawingService drawingService = new DrawingService(new DrawingInMemoryAdapter());
        drawingServicePort = new DrawingServiceAdapter(drawingService);
        TicketService ticketService = new TicketService(
                ticketRepositoryPort, notificationPort, numbersGeneratorPort, drawingServicePort);
        ticketServiceAdapter = new TicketServiceAdapter(ticketService);
    }

    @Test
    void shouldAddNewTicketWhenAnonymousUserHasChosenNumbers() {
        // given
        DrawingType drawingType = DrawingType.LOTTO;
        List<Integer> mainNumbers = List.of(1,2,3,4,5,6);
        TicketRequestDto ticketRequestDto = TicketRequestDto.builder()
                .userId(null)
                .drawingTypeId(drawingType.getId())
                .numberOfDrawings(1)
                .numbersAutogenerated(false)
                .mainNumbers(mainNumbers)
                .extraNumbers(null)
                .build();
        TicketResponseDto expectedResult = TicketMapper.toTicketResponseDto(ticketRequestDto);

        // when
        TicketResponseDto result = ticketServiceAdapter.addTicket(ticketRequestDto);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.drawingTypeName()).isEqualTo(expectedResult.drawingTypeName()),
                () -> assertThat(result.numberOfDrawings()).isEqualTo(expectedResult.numberOfDrawings()),
                () -> assertThat(result.numbers().getMainNumbers()).isEqualTo(expectedResult.numbers().getMainNumbers()),
                () -> assertThat(result.numbers().getExtraNumbers()).isEqualTo(expectedResult.numbers().getExtraNumbers())
        );
    }

    @Test
    void shouldReturnTicketResponseDtoListOfSpecificUser() {
        // given
        String userId = "some-user-id";
        TicketRequestDto ticketRequestDto;
        ticketRequestDto = TicketRequestDto.builder()
                .userId("")
                .drawingTypeId(DrawingType.LOTTO.getId())
                .numberOfDrawings(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId("some-other-user-id")
                .drawingTypeId(DrawingType.LOTTO.getId())
                .numberOfDrawings(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .drawingTypeId(DrawingType.LOTTO.getId())
                .numberOfDrawings(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));
        ticketRequestDto = TicketRequestDto.builder()
                .userId(userId)
                .drawingTypeId(DrawingType.LOTTO.getId())
                .numberOfDrawings(1)
                .numbersAutogenerated(true)
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        ticketRepositoryPort.save(TicketMapper.toTicket(ticketRequestDto));

        // when
        List<TicketResponseDto> result = ticketServiceAdapter.getTicketsByUserId(userId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull().hasSize(2),
                () -> assertThat(result.stream().map(TicketResponseDto::userId).toList()).containsOnly(userId)
        );
    }

}