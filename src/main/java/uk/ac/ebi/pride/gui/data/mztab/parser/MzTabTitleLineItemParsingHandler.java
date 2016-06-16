package uk.ac.ebi.pride.gui.data.mztab.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.gui.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-16 12:41
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public abstract class MzTabTitleLineItemParsingHandler extends MetaDataLineItemParsingHandler {
    private static final Logger logger = LoggerFactory.getLogger(MzTabTitleLineItemParsingHandler.class);

    // Keyword
    protected static final String MZTAB_TITLE_KEYWORD = "title";

    @Override
    protected boolean doParseLineItem(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException {
        String[] lineItems = line.split("\t");
        if ((lineItems.length == 3) && (lineItems[1].equals(MZTAB_TITLE_KEYWORD))) {
            // No further checks are performed to mzTab version value
            return doProcessTitle(context, line, lineNumber, offset, lineItems[2]);
        }
        return false;
    }

    // Delegate processing
    protected abstract boolean doProcessTitle(MzTabParser context, String line, long lineNumber, long offset, String title) throws LineItemParsingHandlerException;
}
