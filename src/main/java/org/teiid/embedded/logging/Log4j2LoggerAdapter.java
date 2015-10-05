/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.teiid.embedded.logging;

import org.teiid.logging.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.teiid.core.util.StringUtil;
import org.teiid.logging.MessageLevel;

public class Log4j2LoggerAdapter implements Logger {

	@Override
    public boolean isEnabled(final String context, final int teiidLevel) {
        final Level level = toLevel(teiidLevel);
        // Log4j caches its own loggers,
        return LogManager.getFormatterLogger(context).isEnabled(level);
    }

    @Override
    public void log(final int teiidLevel, final String context, final Object... msg) {
        log(teiidLevel, context, null, msg);
    }

    @Override
    public void log(final int teiidLevel, final String context, final Throwable t, final Object... msg) {
        final Level level = toLevel(teiidLevel);
        final org.apache.logging.log4j.Logger logger = LogManager.getFormatterLogger(context);
        if (msg.length == 0) {
            logger.log(level, t);
        } else if (msg.length == 1 && !(msg[0] instanceof String)) {
            logger.log(level, msg[0], t);
        } else {
            logger.log(level, StringUtil.toString(msg, " ", false), t); //$NON-NLS-1$
        }
    }

    @Override
    public void putMdc(final String key, final String val) {
        if (key != null && val != null) {
            ThreadContext.put(key, val);
        }
    }

    @Override
    public void removeMdc(final String key) {
        if (key != null) {
            ThreadContext.remove(key);
        }
    }

    @Override
    public void shutdown() {
        ThreadContext.clearAll();
    }

    Level toLevel(final int teiidLevel) {
        switch (teiidLevel) {
        case MessageLevel.NONE:
            return Level.OFF;
        case MessageLevel.CRITICAL:
            return Level.FATAL;
        case MessageLevel.ERROR:
            return Level.ERROR;
        case MessageLevel.WARNING:
            return Level.WARN;
        case MessageLevel.INFO:
            return Level.INFO;
        case MessageLevel.DETAIL:
            return Level.DEBUG;
        case MessageLevel.TRACE:
            return Level.TRACE;
        }
        return Level.ALL;
    }

}
