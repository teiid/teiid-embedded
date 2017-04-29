package org.teiid.embedded.resourceadapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.security.auth.Subject;

import org.jboss.security.SubjectFactory;
import org.junit.Test;
import org.teiid.embedded.resourceadapter.EmbeddedSecuritySubjectFactory;
import org.teiid.jboss.oauth.OAuth10CredentialImpl;

public class TestSecuritySubjectFactory {
    
    @Test
    public void testOAuthSecuritySubject() {
        
        SubjectFactory sf = new EmbeddedSecuritySubjectFactory("picketbox/authentication.conf");
        Subject subject = sf.createSubject("teiid-security-twitter");
        assertEquals(1, subject.getPrivateCredentials().size());
        Object obj = subject.getPrivateCredentials().iterator().next();
        assertTrue(obj instanceof OAuth10CredentialImpl);        
    }

}
