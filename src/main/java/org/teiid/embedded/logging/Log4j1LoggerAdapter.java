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

import org.apache.log4j.Level;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.teiid.core.util.StringUtil;
import org.teiid.logging.Logger;
import org.teiid.logging.MessageLevel;

public class Log4j1LoggerAdapter implements Logger {

	@Override
    public boolean isEnabled(String context, int teiidLevel) {
        Level level = toLevel(teiidLevel);
        return org.apache.log4j.Logger.getLogger(context).isEnabledFor(level);
    }

    @Override
    public void log(int teiidLevel, String context, Object... msg) {
        log(teiidLevel, context, null, msg);
    }

    @Override
    public void log(int teiidLevel, String context, Throwable t, Object... msg) {
        Level level = toLevel(teiidLevel);
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(context);
        if (msg.length == 0) {
            logger.log(level, null, t);
        } else if (msg.length == 1 && !(msg[0] instanceof String)) {
            logger.log(level, msg[0], t);
        } else {
            logger.log(level, StringUtil.toString(msg, " ", false), t); //$NON-NLS-1$
        }

    }

    @Override
    public void putMdc(String key, String val) {
        if (key != null && val != null) {
            MDC.put(key, val);
        }
    }

    @Override
    public void removeMdc(String key) {
        if (key != null) {
            MDC.remove(key);
        }
    }

    @Override
    public void shutdown() {
        MDC.getContext().clear();
        NDC.clear();
    }

    Level toLevel(int teiidLevel) {
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
