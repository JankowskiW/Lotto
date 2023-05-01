package pl.wj.lotto.domain.draw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.gametype.GameTypeSettingsContainer;
import pl.wj.lotto.domain.common.numbers.Numbers;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawRequestDto;
import pl.wj.lotto.domain.draw.model.dto.DrawResponseDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DrawServiceTest {
    @Mock
    private DrawRepositoryPort drawRepositoryPort;
    @InjectMocks
    private DrawService drawService;

    @Test
    void shouldReturnEmptyListWhenThereIsNoDrawWithGivenType() {
        // given
        GameType gameType = GameType.EJP;
        given(drawRepositoryPort.findAllByType(any(GameType.class))).willReturn(new ArrayList<>());

        // when
        List<DrawResponseDto> result = drawService.getDrawsByTypeId(gameType.getId());

        // then
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void shouldReturnDrawsWithGivenType() {
        // given
        GameType gameType = GameType.EJP;
        List<Draw> draws = new ArrayList<>();
        List<DrawResponseDto> expectedResult = DrawMapper.toDrawResponseDtos(draws);
        given(drawRepositoryPort.findAllByType(any(GameType.class))).willReturn(draws);

        // when
        List<DrawResponseDto> result = drawService.getDrawsByTypeId(gameType.getId());

        // then
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldAddDraw() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDateTime = LocalDateTime.now();
        GameType gameType = GameType.EJP;
        DrawRequestDto drawRequestDto = DrawRequestDto.builder()
                .typeId(gameType.getId())
                .mainNumbers(List.of(1,2,3,4,5))
                .extraNumbers(List.of(1,2))
                .build();
        Draw draw = DrawMapper.toDraw(drawRequestDto);
        draw.setId(id);
        draw.setDrawDateTime(drawDateTime);
        DrawResponseDto expectedResult = DrawMapper.toDrawResponseDto(draw);
        given(drawRepositoryPort.save(any(Draw.class)))
                .willAnswer(
                        i -> {
                            Draw d = i.getArgument(0, Draw.class);
                            d.setId(id);
                            d.setDrawDateTime(drawDateTime);
                            return d;
                        }
                );

        // when
        DrawResponseDto result = drawService.addDraw(drawRequestDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnDrawByIdWhenExistsInDatabase() {
        // given
        String id = UUID.randomUUID().toString();
        LocalDateTime drawDateTime = LocalDateTime.now();
        GameType gameType = GameType.LOTTO;
        Numbers numbers = Numbers.builder()
                .gameType(GameType.LOTTO)
                .drawDateTimeSettings(GameTypeSettingsContainer.getGameTypeSettings(gameType).drawDateTimeSettings())
                .mainNumbers(List.of(1,2,3,4,5,6))
                .build();
        Draw draw = Draw.builder()
                .id(id)
                .type(GameType.LOTTO)
                .numbers(numbers)
                .drawDateTime(drawDateTime)
                .build();
        DrawResponseDto expectedResult = DrawMapper.toDrawResponseDto(draw);
        given(drawRepositoryPort.findById(anyString())).willReturn(Optional.of(draw));

        // when
        DrawResponseDto result = drawService.getDrawById(id);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenDrawNotExistsInDabase() {
        // given
        String id = UUID.randomUUID().toString();
        given(drawRepositoryPort.findById(anyString())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> drawService.getDrawById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Draw not found");
    }

}