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
package org.teiid.embedded.helper.ironJacamar;

import java.util.Objects;
import java.util.function.Consumer;

import javax.resource.ResourceException;
import javax.sql.DataSource;

import org.jboss.jca.adapters.jdbc.local.LocalManagedConnectionFactory;
import org.jboss.jca.core.api.connectionmanager.pool.PoolConfiguration;
import org.jboss.jca.core.connectionmanager.notx.NoTxConnectionManagerImpl;
import org.jboss.jca.core.connectionmanager.pool.mcp.LeakDumperManagedConnectionPool;
import org.jboss.jca.core.connectionmanager.pool.strategy.OnePool;
import org.teiid.embedded.helper.IronJacamarHelper;

public class IronJacamarHelperImpl implements IronJacamarHelper {

    @Override
    public DataSource newNoTxDataSource(Consumer<Configuration> consumer) throws ResourceException {
        
        Objects.requireNonNull(consumer);
        Configuration config = new Configuration();
        consumer.accept(config);
        
        Objects.requireNonNull(config.localManagedConnectionFactory());
        LocalManagedConnectionFactory mcf = config.localManagedConnectionFactory();
        
        NoTxConnectionManagerImpl cm = new NoTxConnectionManagerImpl();
        String mcp = LeakDumperManagedConnectionPool.class.getName();
        PoolConfiguration poolConfig = config.poolConfiguration();
        if(poolConfig == null) {
            poolConfig = new PoolConfiguration();
        }
        OnePool pool = new OnePool(mcf, poolConfig, false, true, mcp);
        pool.setConnectionManager(cm);
        cm.setPool(pool);
        
        return (DataSource) mcf.createConnectionFactory(cm);
    }

}
