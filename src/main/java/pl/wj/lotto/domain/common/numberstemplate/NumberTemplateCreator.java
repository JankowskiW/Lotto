package pl.wj.lotto.domain.common.numberstemplate;

import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.common.numberstemplate.model.EuroJackpotNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.KenoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.LottoNumbers;
import pl.wj.lotto.domain.common.numberstemplate.model.Quick600Numbers;

public class NumberTemplateCreator {
    public static NumbersTemplate createNumbersTemplateByDrawingType(DrawingType drawingType) {
        return switch(drawingType) {
            case LOTTO -> new LottoNumbers();
            case Q600 -> new Quick600Numbers();
            case EJP -> new EuroJackpotNumbers();
            case KENO -> new KenoNumbers();
        };
    }
}
