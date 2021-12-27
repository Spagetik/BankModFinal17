package org.spagetik.bankmod.exeptions;

public class WrongResponseKey extends Exception{
    public WrongResponseKey(String errorMessage) {
        super(errorMessage);
    }
}
