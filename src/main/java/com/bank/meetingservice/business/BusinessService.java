package com.bank.meetingservice.business;

public interface BusinessService<I,O> {

    O doAction(I req);


}
