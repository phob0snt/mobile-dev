package com.x2ketarol.askon.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.x2ketarol.askon.data.local.dao.BookingDao;
import com.x2ketarol.askon.data.local.dao.ExpertDao;
import com.x2ketarol.askon.data.local.dao.MessageDao;
import com.x2ketarol.askon.data.local.entity.BookingEntity;
import com.x2ketarol.askon.data.local.entity.ExpertEntity;
import com.x2ketarol.askon.data.local.entity.MessageEntity;

@Database(entities = {ExpertEntity.class, BookingEntity.class, MessageEntity.class}, version = 1)
public abstract class AskonDatabase extends RoomDatabase {
    
    private static AskonDatabase instance;
    
    public abstract ExpertDao expertDao();
    public abstract BookingDao bookingDao();
    public abstract MessageDao messageDao();
    
    public static synchronized AskonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AskonDatabase.class,
                "askon_database"
            ).allowMainThreadQueries()  // Для упрощения, в production использовать асинхронные запросы
             .build();
        }
        return instance;
    }
}
