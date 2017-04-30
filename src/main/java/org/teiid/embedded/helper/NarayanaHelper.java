package org.teiid.embedded.helper;

import java.util.function.Consumer;

import javax.transaction.TransactionManager;

import org.teiid.embedded.helper.narayana.Configuration;
import org.teiid.embedded.helper.narayana.NarayanaHelperImpl;

public interface NarayanaHelper {
    
    TransactionManager transactionManager();
    
    TransactionManager transactionManager(Consumer<Configuration> consumer);
    
    NarayanaHelper Factory = new NarayanaHelperImpl();

}
