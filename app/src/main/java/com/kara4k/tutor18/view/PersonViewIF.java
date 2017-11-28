package com.kara4k.tutor18.view;


import com.kara4k.tutor18.model.Person;

public interface PersonViewIF extends ViewIF {

    void showPersonDetails(Person person);

    void showPersonEdits(Person person);

    void showPersonCreator();

    void closeView();
}
