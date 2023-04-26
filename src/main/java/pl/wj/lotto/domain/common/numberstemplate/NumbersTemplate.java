package pl.wj.lotto.domain.common.numberstemplate;

import java.util.List;

public interface NumbersTemplate {
    void setNumbers(List<Integer> mainNumbers, List<Integer> extraNumbers);
    List<Integer> getMainNumbers();
    default List<Integer> getExtraNumbers() {return List.of();}
}
