package com.camunda.poc.starter.usecase.renewal;

import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import org.camunda.bpm.engine.HistoryService;
import org.springframework.stereotype.Service;

import com.camunda.poc.starter.usecase.renewal.entity.Message;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import com.camunda.poc.starter.usecase.renewal.repo.MessageRepository;

@Service
public class RenewalCleanupServiceImpl implements RenewalCleanupService {

	public RenewalCleanupServiceImpl(){}
	public RenewalCleanupServiceImpl(RenewalRepository renewalRepository, MessageRepository messageRepository, HistoryService historyService){
		this.renewalRepository = renewalRepository;
		this.messageRepository = messageRepository;
		this.historyService = historyService;
	}
    private RenewalRepository renewalRepository;
    
    private MessageRepository messageRepository;

    private HistoryService historyService;
    
	public RenewalRepository getRenewalRepository() {
		return renewalRepository;
	}

	public void setRenewalRepository(RenewalRepository renewalRepository) {
		this.renewalRepository = renewalRepository;
	}

	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	
	public void clean(){
		List<Renewal> renewals = renewalRepository.findStarted();
		for(Renewal renewal : renewals){
			boolean complete = RenewalUtil.checkTaskComplete(historyService, renewal.getBusinessKey(), "end");
			if (complete){
				renewal.setRenewalCompleted(true);
				renewal.setWorkflowState("End");
				renewalRepository.save(renewal);
			}
		}
		
		for(Renewal renewal : renewals){
			boolean complete = RenewalUtil.checkTaskComplete(historyService, renewal.getBusinessKey(), "end");
			if (complete){						
				List<Message> messages = renewal.getMessages();
				for(Message message : messages){
					message.setArchived(true);
					messageRepository.save(message);
				}
			}
		}
	}

}
