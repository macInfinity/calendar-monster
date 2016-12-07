package com.cmcllc.parse;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

/**
 * Created by chrismaki on 12/4/16.
 */
public class ParseStringTrim extends CellProcessorAdaptor {

  public ParseStringTrim() {
    super();
  }

  public ParseStringTrim(CellProcessor next) {
    super(next);
  }

  @Override
  public Object execute(Object value, CsvContext context) {
    validateInputNotNull(value, context);

    return next.execute(StringUtils.trimToNull(value.toString()), context);
  }
}
