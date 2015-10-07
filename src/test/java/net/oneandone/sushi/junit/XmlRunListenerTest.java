package net.oneandone.sushi.junit;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

/**
 * Created by mirko on 07.10.15.
 */
public class XmlRunListenerTest {

    final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void testTestRunFinished() throws Exception {
        final XmlRunListener sut = new XmlRunListener(out);
        sut.testRunFinished(null);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", out.toString("utf-8"));
    }
}