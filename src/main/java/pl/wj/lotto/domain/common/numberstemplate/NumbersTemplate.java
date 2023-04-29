package pl.wj.lotto.domain.common.numberstemplate;

import pl.wj.lotto.domain.common.drawtime.DrawTimeCheckable;

import java.util.List;

public interface NumbersTemplate extends DrawTimeCheckable {
    void setNumbers(List<Integer> mainNumbers, List<Integer> extraNumbers);
    List<Integer> getMainNumbers();
    default List<Integer> getExtraNumbers() {return List.of();}
}
