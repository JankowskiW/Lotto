package pl.wj.lotto.domain.ticket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Ticket {
    String id;
    String userId;
    String drawingTypeId;
}
