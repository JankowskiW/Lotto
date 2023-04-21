package pl.wj.lotto.domain.common.notification;

import java.util.List;

public interface NotificationPort {
    void send(List<String> recipients, String message);
    void send(String recipient, String message);
}
