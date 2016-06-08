package uk.ac.ebi.pride.gui.data.mztab;

import com.itextpdf.text.Meta;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.gui.data.mztab.exceptions.InvalidMetaDataException;
import uk.ac.ebi.pride.gui.data.mztab.model.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab
 * Timestamp: 2016-06-08 09:54
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */

public class MetaDataTest {

    // Testing getters setters is absurd

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void emptyMetadataDoesnotValidate() {
        MetaData metaData = new MetaData();
        exception.expect(InvalidMetaDataException.class);
        metaData.validate();
    }

    @Test
    public void minimumInformationForValidation() throws MalformedURLException {
        MetaData metaData = new MetaData();
        metaData.setTitle("Test Title");
        metaData.setVersion("1.0");
        metaData.setMode(MetaData.MzTabMode.IDENTIFICATION);
        metaData.setType(MetaData.MzTabType.COMPLETE);
        metaData.setDescription("Sample Description");
        MsRunFormat msRunFormat = new MsRunFormat("PSI-MS", "MS:1001062", "Mascot MGF file", CvParameter.DEFAULT_VALUE);
        MsRunIdFormat msRunIdFormat = new MsRunIdFormat("PSI-MS", "MS:1000774", "multiple peak list nativeID format", CvParameter.DEFAULT_VALUE);
        MsRun msRun = new MsRun(msRunFormat, msRunIdFormat, new URL("file:/test.file"));
        metaData.addMsRun(msRun);
        metaData.validate();
    }
}