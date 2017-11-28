package com.kara4k.tutor18.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity(active = true, nameInDb = "lessons")
public class Lesson implements Serializable{

    public static final long serialVersionUID = 40L;

    @Id
    Long id;
    Long personId;
    int dayOfWeek;
    int startHour;
    int startMin;
    int duration;
    double price;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 610143130)
    private transient LessonDao myDao;
    @Generated(hash = 934759525)
    public Lesson(Long id, Long personId, int dayOfWeek, int startHour,
            int startMin, int duration, double price) {
        this.id = id;
        this.personId = personId;
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.startMin = startMin;
        this.duration = duration;
        this.price = price;
    }
    @Generated(hash = 1669664117)
    public Lesson() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPersonId() {
        return this.personId;
    }
    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    public int getDayOfWeek() {
        return this.dayOfWeek;
    }
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public int getStartHour() {
        return this.startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    public int getStartMin() {
        return this.startMin;
    }
    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2078826279)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLessonDao() : null;
    }


}
