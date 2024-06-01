/**
 * This class is a custom converter for logback that converts the thread ID of the logging event into a string.
 */
package com.kapia.jobboard.api.data.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadIdConverter extends ClassicConverter {
    @Override
    public String convert(final ILoggingEvent e) {
        return String.valueOf(Thread.currentThread().threadId());
    }
}