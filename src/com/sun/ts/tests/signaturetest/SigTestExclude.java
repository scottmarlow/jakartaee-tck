package com.sun.ts.tests.signaturetest;

import com.sun.tdk.signaturetest.core.ExcludeException;
import com.sun.tdk.signaturetest.model.ClassDescription;
import com.sun.tdk.signaturetest.model.MemberDescription;

/**
 * SigTestExclude
 *
 * @author Scott Marlow
 */
public class SigTestExclude implements com.sun.tdk.signaturetest.core.Exclude {

    private static String history = "";
    
    @Override
    public String[] parseParameters(String[] strings) {
        return strings;
    }

    @Override
    public void check(ClassDescription classDescription, MemberDescription memberDescription) throws ExcludeException {
        String detail = "name= " + classDescription.getName() + 
                ", qualified name=" + classDescription.getQualifiedName() + 
                ", toString= " + classDescription.toString();
        history = history + "\n" + detail;
    }

    @Override
    public String report() {
        return history;
    }
}
