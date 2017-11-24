package com.kara4k.tutor18.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "events")
public class Event {

    public static final int NOT_HELD = 0;
    public static final int HELD = 1;
    public static final int RESCHEDULED = 2;

    @Id
    Long id;
    Long personId;
    Long lessonId;
    Long date;
    int isHeld;
    Long rescheduledToId;
    boolean isPayment;
    boolean isPaid;
    String subjects;
    String note;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1542254534)
    private transient EventDao myDao;
    @Generated(hash = 1934729263)
    public Event(Long id, Long personId, Long lessonId, Long date, int isHeld,
            Long rescheduledToId, boolean isPayment, boolean isPaid,
            String subjects, String note) {
        this.id = id;
        this.personId = personId;
        this.lessonId = lessonId;
        this.date = date;
        this.isHeld = isHeld;
        this.rescheduledToId = rescheduledToId;
        this.isPayment = isPayment;
        this.isPaid = isPaid;
        this.subjects = subjects;
        this.note = note;
    }
    @Generated(hash = 344677835)
    public Event() {
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
    public Long getLessonId() {
        return this.lessonId;
    }
    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
    public Long getDate() {
        return this.date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    public int getIsHeld() {
        return this.isHeld;
    }
    public void setIsHeld(int isHeld) {
        this.isHeld = isHeld;
    }
    public Long getRescheduledToId() {
        return this.rescheduledToId;
    }
    public void setRescheduledToId(Long rescheduledToId) {
        this.rescheduledToId = rescheduledToId;
    }
    public boolean getIsPayment() {
        return this.isPayment;
    }
    public void setIsPayment(boolean isPayment) {
        this.isPayment = isPayment;
    }
    public boolean getIsPaid() {
        return this.isPaid;
    }
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    public String getSubjects() {
        return this.subjects;
    }
    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
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
    @Generated(hash = 1459865304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEventDao() : null;
    }
}
