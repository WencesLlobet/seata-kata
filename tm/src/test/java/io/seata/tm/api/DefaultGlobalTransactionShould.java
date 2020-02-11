package io.seata.tm.api;

import io.seata.core.exception.TransactionException;
import io.seata.core.model.GlobalStatus;
import io.seata.core.model.TransactionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultGlobalTransactionShould {

  public TransactionManager transactionManager = mock(TransactionManager.class);

  @Test
  void should_repeat_attempts() throws TransactionException {
    DefaultGlobalTransaction defaultGlobalTransaction = new DefaultGlobalTransaction("",null,null, transactionManager);
    when(transactionManager.commit(""))
        .thenThrow(new TransactionException(""))
        .thenThrow(new TransactionException(""))
        .thenReturn(GlobalStatus.Committed);

    defaultGlobalTransaction.commit(); 

    verify(transactionManager,times(3)).commit("");
  }

  @Test
  void should_stop_repeating_attempts_and_throw_when_limit_is_reached() throws TransactionException {
    DefaultGlobalTransaction defaultGlobalTransaction = new DefaultGlobalTransaction("",null,null, transactionManager);
    when(transactionManager.commit(""))
        .thenThrow(new TransactionException(""));

    try {
      defaultGlobalTransaction.commit();
    }catch (TransactionException e){
      verify(transactionManager,times(4)).commit("");
      return;
    }

    fail();
  }
}