package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mshsie.masterproject.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
