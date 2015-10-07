package net.oneandone.sushi.junit;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Created by mirko on 07.10.15.
 */
@RunListener.ThreadSafe
public class XmlRunListener extends RunListener {

    /** Needed for conversion of final result to bytes. */
    private static final Charset UTF8 = Charset.forName("utf-8");

    private final OutputStream out;

    final XMLStreamWriter writer;

    public XmlRunListener(OutputStream out){
        this.out = out;
        try {
            writer = XMLOutputFactory.newInstance().createXMLStreamWriter(out, "utf-8");
            writer.writeProcessingInstruction("xml version=\"1.0\" encoding=\"UTF-8\"");
        } catch (XMLStreamException e) {
            throw new RuntimeException("Could not create XMLStreamWriter", e);
        }
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        writer.close();
    }
}
