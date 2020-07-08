package com.camunda.poc.starter.history.event.handler.plugin;

import org.camunda.bpm.engine.impl.cfg.TransactionListener;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

import java.util.logging.Logger;

public class MyHistoryEventCommitListener implements TransactionListener {

    private Logger _logger = Logger.getLogger(Class.class.getName());

    private String _settings;
    private HistoryEvent _historyEvent;

    public MyHistoryEventCommitListener(String settings, HistoryEvent historyEvent) {
        _settings = settings;
        _historyEvent = historyEvent;
    }

    @Override
    public void execute(CommandContext commandContext) {
        _logger.info(" ***** HISTORY-EVENT COMMITTED: "+ _historyEvent.getEventType()+ "\n");
    }

}