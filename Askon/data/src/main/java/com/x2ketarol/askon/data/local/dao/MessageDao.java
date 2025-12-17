package com.x2ketarol.askon.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.x2ketarol.askon.data.local.entity.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    
    @Query("SELECT * FROM messages WHERE (senderId = :userId OR receiverId = :userId) ORDER BY timestamp ASC")
    List<MessageEntity> getMessagesForUser(String userId);
    
    @Query("SELECT * FROM messages WHERE (senderId = :userId AND receiverId = :otherUserId) OR (senderId = :otherUserId AND receiverId = :userId) ORDER BY timestamp ASC")
    List<MessageEntity> getConversation(String userId, String otherUserId);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(MessageEntity message);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessages(List<MessageEntity> messages);
    
    @Query("DELETE FROM messages")
    void deleteAll();
}
