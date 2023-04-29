package pl.wj.lotto.domain.common.numberstemplate;

import pl.wj.lotto.domain.common.drawingtype.DrawingTime;

import java.util.List;

public interface NumbersTemplate {
    void setNumbers(List<Integer> mainNumbers, List<Integer> extraNumbers);
    List<Integer> getMainNumbers();
    default List<Integer> getExtraNumbers() {return List.of();}
    DrawingTime getDrawingTime();
}
