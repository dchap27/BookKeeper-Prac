package com.room.ps.bookkeeper;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public Date toDate(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long toLong(Date value){
        return value == null ? null : value.getTime();
    }
}
