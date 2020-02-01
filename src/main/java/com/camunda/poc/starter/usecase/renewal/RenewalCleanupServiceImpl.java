package com.camunda.poc.starter.usecase.renewal;

import java.util.List;

import org.camunda.bpm.engine.HistoryService;
import org.springframework.stereotype.Service;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.entity.Message;
import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import com.camunda.poc.starter.usecase.renewal.repo.MessageRepository;

@Service
public class RenewalCleanupServiceImpl implements RenewalCleanupService {

	public RenewalCleanupServiceImpl(){}
	public RenewalCleanupServiceImpl(LeaseRepository leaseRepository, MessageRepository messageRepository, HistoryService historyService){
		this.leaseRepository = leaseRepository;
		this.messageRepository = messageRepository;
		this.historyService = historyService;
	}
    private LeaseRepository leaseRepository;
    
    private MessageRepository messageRepository;

    private HistoryService historyService;
    
	public LeaseRepository getLeaseRepository() {
		return leaseRepository;
	}

	public void setLeaseRepository(LeaseRepository leaseRepository) {
		this.leaseRepository = leaseRepository;
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
		List<Lease> leases = leaseRepository.findStarted();
		for(Lease lease : leases){
			boolean complete = RenewalUtil.checkTaskComplete(historyService, lease.getBusinessKey(), "end");
			if (complete){
				lease.setRenewalCompleted(true);
				lease.setWorkflowState("End");
				leaseRepository.save(lease);
			}
		}
		
		for(Lease lease : leases){
			boolean complete = RenewalUtil.checkTaskComplete(historyService, lease.getBusinessKey(), "end");
			if (complete){						
				List<Message> messages = lease.getMessages();
				for(Message message : messages){
					message.setArchived(true);
					messageRepository.save(message);
				}
			}
		}
	}

}
