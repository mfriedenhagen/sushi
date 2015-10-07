package net.oneandone.sushi.junit;

import net.oneandone.sushi.io.BufferTest;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by mirko on 07.10.15.
 */
public class XmlRunListenerTest {

    final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void testTestRunFinished() throws Exception {
        final XmlRunListener sut = new XmlRunListener(out, "testsuite-name");
        final Description description = Description.createTestDescription(XmlRunListenerTest.class.getName(), "foo");
        sut.testStarted(description);
        Thread.sleep(50);
        sut.testFinished(description);
        sut.testRunFinished(null);
        final String actual = out.toString("utf-8");
        assertThat(actual, startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testsuite name=\"testsuite-name\"><testcase name=\"foo(net.oneandone.sushi.junit.XmlRunListenerTest)\" classname=\"net.oneandone.sushi.junit.XmlRunListenerTest\" time=\"0.05"));
        assertThat(actual, endsWith("</testsuite>"));
    }

    @Test
    public void testRun() throws Exception {
        final XmlRunListener sut = new XmlRunListener(out, "testsuite-name");
        final JUnitCore core = new JUnitCore();
        core.addListener(sut);
        core.run(BufferTest.class);
        final String actual = out.toString();
        assertThat(actual, containsString("testcase name=\"eadLine("));
    }
}