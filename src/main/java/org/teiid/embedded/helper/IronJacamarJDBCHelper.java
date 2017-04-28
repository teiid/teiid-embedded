package org.teiid.embedded.helper;

import javax.resource.ResourceException;
import javax.sql.DataSource;

import org.jboss.jca.adapters.jdbc.local.LocalManagedConnectionFactory;

public interface IronJacamarJDBCHelper {
    
    DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password) throws ResourceException;
    
    DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password, String connectionProperties) throws ResourceException;
    
    IronJacamarJDBCHelperImpl Factory = new IronJacamarJDBCHelperImpl();

    class IronJacamarJDBCHelperImpl implements IronJacamarJDBCHelper {
        
        @Override
        public DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password) throws ResourceException {
            return newNoTxDataSource(driverClass, connURL, user, password, null);
        }

        @Override
        public DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password, String connectionProperties) throws ResourceException {
            
            LocalManagedConnectionFactory mcf = new LocalManagedConnectionFactory();
            
            mcf.setDriverClass(driverClass);
            mcf.setConnectionURL(connURL);
            mcf.setUserName(user);
            mcf.setPassword(password);
            mcf.setConnectionProperties(connectionProperties);
             
            return (DataSource) mcf.createConnectionFactory();
        }

        
        
    }
}
