package com.ntt.transactions.account.application.port.out;

public interface AccountMessagingPort {
  Long sendIdReceiveRef(String idNumber);
}
