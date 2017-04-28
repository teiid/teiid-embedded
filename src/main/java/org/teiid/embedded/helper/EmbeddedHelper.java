package org.teiid.embedded.helper;

import javax.resource.ResourceException;
import javax.sql.DataSource;


public interface EmbeddedHelper extends IronJacamarJDBCHelper {
    
    EmbeddedHelper Factory = new EmbeddedHelperImpl();

    class EmbeddedHelperImpl implements EmbeddedHelper {
        
        @Override
        public DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password) throws ResourceException {
            return IronJacamarJDBCHelper.Factory.newNoTxDataSource(driverClass, connURL, user, password);
        }
        
        @Override
        public DataSource newNoTxDataSource(String driverClass, String connURL, String user, String password, String connectionProperties) throws ResourceException {
            return IronJacamarJDBCHelper.Factory.newNoTxDataSource(driverClass, connURL, user, password, connectionProperties);
        }
        
    }
}
