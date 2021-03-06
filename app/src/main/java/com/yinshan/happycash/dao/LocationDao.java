package com.yinshan.happycash.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCATION".
*/
public class LocationDao extends AbstractDao<Location, Long> {

    public static final String TABLENAME = "LOCATION";

    /**
     * Properties of entity Location.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LocType = new Property(1, Integer.class, "locType", false, "LOC_TYPE");
        public final static Property Latitude = new Property(2, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(3, Double.class, "longitude", false, "LONGITUDE");
        public final static Property Altitude = new Property(4, Double.class, "altitude", false, "ALTITUDE");
        public final static Property DateTime = new Property(5, String.class, "dateTime", false, "DATE_TIME");
    }


    public LocationDao(DaoConfig config) {
        super(config);
    }
    
    public LocationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"LOC_TYPE\" INTEGER," + // 1: locType
                "\"LATITUDE\" REAL," + // 2: latitude
                "\"LONGITUDE\" REAL," + // 3: longitude
                "\"ALTITUDE\" REAL," + // 4: altitude
                "\"DATE_TIME\" TEXT);"); // 5: dateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Location entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer locType = entity.getLocType();
        if (locType != null) {
            stmt.bindLong(2, locType);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(3, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(4, longitude);
        }
 
        Double altitude = entity.getAltitude();
        if (altitude != null) {
            stmt.bindDouble(5, altitude);
        }
 
        String dateTime = entity.getDateTime();
        if (dateTime != null) {
            stmt.bindString(6, dateTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Location entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer locType = entity.getLocType();
        if (locType != null) {
            stmt.bindLong(2, locType);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(3, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(4, longitude);
        }
 
        Double altitude = entity.getAltitude();
        if (altitude != null) {
            stmt.bindDouble(5, altitude);
        }
 
        String dateTime = entity.getDateTime();
        if (dateTime != null) {
            stmt.bindString(6, dateTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Location readEntity(Cursor cursor, int offset) {
        Location entity = new Location( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // locType
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // latitude
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // longitude
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // altitude
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // dateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Location entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLocType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setLatitude(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setLongitude(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setAltitude(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setDateTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Location entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Location entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Location entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
