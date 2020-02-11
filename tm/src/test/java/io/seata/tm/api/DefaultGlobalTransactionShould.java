package io.seata.tm.api;

import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DefaultGlobalTransactionShould {

  public TransactionManager transactionManager = mock(TransactionManager.class);

  @Test
  void should_repeat_attempts() throws TransactionException {
    DefaultGlobalTransaction defaultGlobalTransaction = new DefaultGlobalTransaction("",null,null, transactionManager);
    defaultGlobalTransaction.commit();
  }
}