package com.kara4k.tutor18.model;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;

@Entity(active = true, nameInDb = "events")
public class Event implements Serializable {

    public static final long serialVersionUID = 40L;
    public static final int UNDEFINED = 0;
    public static final int HELD = 1;
    public static final int NOT_HELD = 2;
    public static final int RESCHEDULED = 3;

    @Id
    Long id;
    Long personId;
    @ToOne(joinProperty = "personId")
    Person person;
    Long lessonId;
    @ToOne(joinProperty = "lessonId")
    Lesson lesson;
    int state;
    Long rescheduledToId;
    Long rescheduledFromId;
    boolean isPayment;
    boolean isPaid;
    double expectedPrice;
    double price;
    String subjects;
    String note;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1542254534)
    private transient EventDao myDao;
    @Generated(hash = 164549064)
    public Event(Long id, Long personId, Long lessonId, int state,
            Long rescheduledToId, Long rescheduledFromId, boolean isPayment,
            boolean isPaid, double expectedPrice, double price, String subjects,
            String note) {
        this.id = id;
        this.personId = personId;
        this.lessonId = lessonId;
        this.state = state;
        this.rescheduledToId = rescheduledToId;
        this.rescheduledFromId = rescheduledFromId;
        this.isPayment = isPayment;
        this.isPaid = isPaid;
        this.expectedPrice = expectedPrice;
        this.price = price;
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
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Long getRescheduledToId() {
        return this.rescheduledToId;
    }
    public void setRescheduledToId(Long rescheduledToId) {
        this.rescheduledToId = rescheduledToId;
    }
    public Long getRescheduledFromId() {
        return this.rescheduledFromId;
    }
    public void setRescheduledFromId(Long rescheduledFromId) {
        this.rescheduledFromId = rescheduledFromId;
    }
    public boolean isPayment() {
        return this.isPayment;
    }
    public void setIsPayment(boolean isPayment) {
        this.isPayment = isPayment;
    }
    public boolean isPaid() {
        return this.isPaid;
    }
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    public double getExpectedPrice() {
        return this.expectedPrice;
    }
    public void setExpectedPrice(double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
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
    @Generated(hash = 1154009267)
    private transient Long person__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1752520167)
    public Person getPerson() {
        Long __key = this.personId;
        if (person__resolvedKey == null || !person__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersonDao targetDao = daoSession.getPersonDao();
            Person personNew = targetDao.load(__key);
            synchronized (this) {
                person = personNew;
                person__resolvedKey = __key;
            }
        }
        return person;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1834876435)
    public void setPerson(Person person) {
        synchronized (this) {
            this.person = person;
            personId = person == null ? null : person.getId();
            person__resolvedKey = personId;
        }
    }
    @Generated(hash = 1079550820)
    private transient Long lesson__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 688473547)
    public Lesson getLesson() {
        Long __key = this.lessonId;
        if (lesson__resolvedKey == null || !lesson__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LessonDao targetDao = daoSession.getLessonDao();
            Lesson lessonNew = targetDao.load(__key);
            synchronized (this) {
                lesson = lessonNew;
                lesson__resolvedKey = __key;
            }
        }
        return lesson;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 601950609)
    public void setLesson(Lesson lesson) {
        synchronized (this) {
            this.lesson = lesson;
            lessonId = lesson == null ? null : lesson.getId();
            lesson__resolvedKey = lessonId;
        }
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
    public boolean getIsPayment() {
        return this.isPayment;
    }
    public boolean getIsPaid() {
        return this.isPaid;
    }



}
