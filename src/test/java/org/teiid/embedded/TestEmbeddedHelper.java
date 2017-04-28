package org.teiid.embedded;

import static org.teiid.embedded.util.JDBCUtils.close;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.teiid.embedded.helper.EmbeddedHelper;

public class TestEmbeddedHelper {
    
    @Test
    public void testDataSource() throws Exception {
        DataSource ds = EmbeddedHelper.Factory.newNoTxDataSource("org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa");
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }

    @Test
    public void testDataSourceWithProperties() throws Exception {
        DataSource ds = EmbeddedHelper.Factory.newNoTxDataSource("org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "sa", "foo=1;bar=2;boo=3");
        Connection conn = ds.getConnection();
        assertNotNull(conn);
        close(conn);
    }


}
