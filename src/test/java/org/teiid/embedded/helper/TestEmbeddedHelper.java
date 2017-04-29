package org.teiid.embedded.helper;

import static org.junit.Assert.assertNotNull;
import static org.teiid.embedded.helper.utils.JDBCUtils.close;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

public class TestEmbeddedHelper {
    
    @Test
    public void testNoTxDataSource() throws Exception {
        
        DataSource ds = EmbeddedHelper.Factory.newNoTxDataSource(c -> c.localManagedConnectionFactory(mcf -> {
            mcf.setDriverClass("org.h2.Driver");
            mcf.setConnectionURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            mcf.setUserName("sa");
            mcf.setPassword("sa");
        }));
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }
    
    @Test
    public void testNoTxDataSource_1() throws Exception {
        
        DataSource ds = EmbeddedHelper.Factory.newNoTxDataSource(c -> c.localManagedConnectionFactory(mcf -> {
            mcf.setDriverClass("org.h2.Driver");
            mcf.setConnectionURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            mcf.setUserName("sa");
            mcf.setPassword("sa");
        }).poolConfiguration(p -> {
            p.setMaxSize(30);
            p.setMinSize(5);
            p.setBlockingTimeout(30000);
            p.setIdleTimeoutMinutes(10);
        }));
        
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }

    @Test(expected = Exception.class)
    public void testNoTxDataSource_2() throws Exception {
        
        DataSource ds = EmbeddedHelper.Factory.newNoTxDataSource(c -> c.localManagedConnectionFactory(mcf -> {
            mcf.setDriverClass("org.h2.Driver");
            mcf.setConnectionURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            mcf.setUserName("sa");
            mcf.setPassword("sa");
        }));
        Connection conn = ds.getConnection();
        conn.commit();
    }

}
