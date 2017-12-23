package com.kara4k.tutor18.view;


import com.kara4k.tutor18.model.Lesson;

import java.util.List;

public interface SchedulerIF extends ViewIF {
    void showMessage(String message);

    void showPersonsDialog(CharSequence[] dialogItems);

    void setPersonSummary(String summary);

    void showLessonsDialog(CharSequence[] lessonItems, List<Lesson> lessons);

    void setLessonSummary(String summary);

    void setLessonVisibility(int visibility);
}
