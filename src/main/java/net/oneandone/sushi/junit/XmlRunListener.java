package net.oneandone.sushi.junit;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by mirko on 07.10.15.
 */
@RunListener.ThreadSafe
public class XmlRunListener extends RunListener {

    /** Needed for conversion of final result to bytes. */
    private static final Charset UTF8 = Charset.forName("utf-8");

    private final OutputStream out;

    final XMLStreamWriter writer;

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    public XmlRunListener(OutputStream out, String testSuiteName){
        this.out = out;
        try {
            writer = XMLOutputFactory.newInstance().createXMLStreamWriter(out, "utf-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("testsuite");
            writer.writeAttribute("name", testSuiteName);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Could not create XMLStreamWriter", e);
        }
    }

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);
        writer.writeStartElement("testcase");
        writer.writeAttribute("name", description.getDisplayName());
        writer.writeAttribute("classname", description.getClassName());
        startTime.set(System.currentTimeMillis());
    }

    @Override
    public void testFinished(Description description) throws Exception {
        super.testFinished(description);
        writer.writeAttribute("time",
                String.format(Locale.ENGLISH, "%.3f", (System.currentTimeMillis() - startTime.get())/1000.0));
        writer.writeEndElement();
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        writer.writeStartElement("failure");
        writer.writeAttribute("message", failure.getMessage());
        writer.writeAttribute("type", failure.);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();
    }
}
