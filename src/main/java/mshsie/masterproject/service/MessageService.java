package mshsie.masterproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Message;
import mshsie.masterproject.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;
	
	// Ruan nje mesazh te ri
	public void saveMessage(Message message) {
		message = messageRepository.save(message);
	}
}
