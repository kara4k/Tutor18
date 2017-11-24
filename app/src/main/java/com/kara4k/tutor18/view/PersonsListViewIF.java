package com.kara4k.tutor18.view;


import com.kara4k.tutor18.model.Person;

public interface PersonsListViewIF extends ListViewIF<Person> {

    void showDetails(Person person);
}
