package ArraysSort;

import java.text.Collator;
import java.util.*;

class Person1 {
    private String Name;
    private String Surname;
    private int ID;

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public int getID() {
        return ID;
    }

    public Person1(String name, String surname, int ID) {
        Name = name;
        Surname = surname;
        this.ID = ID;
    }
}

class Employee extends Person1 {
    private double Salary;

    public double getSalary() {
        return Salary;
    }

    public Employee(String name, String surname, int ID, double salary) {
        super(name, surname, ID);
        Salary = salary;
    }

    @Override
    public String toString() {
        return super.getName() + " " + super.getSurname() + " " + super.getID() + " " + getSalary();
    }
}

class LinkedListSort {
    public static void main(String[] args) {
        LinkedList<Employee> list = new LinkedList<>();
        list.add(new Employee("Va", "Pz", 1, 120.5));
        list.add(new Employee("Dm", "Py", 1, 120.5));
        list.add(new Employee("Dm", "Aa", 1, 120.5));
        list.add(new Employee("Va", "Tu", 1, 140.5));
        list.add(new Employee("Ma", "Nu", 1, 100.5));
        list.add(new Employee("Da", "Gu", 1, 180.5));

        Collections.sort(list, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                int deltaSalary =(int)(o1.getSalary() - o2.getSalary());
                if (deltaSalary != 0)
                    return deltaSalary;
                else
                    return Collator.getInstance().compare(o1.getSurname(), o2.getSurname());
                }
            });

//        list.forEach(System.out::println);
    }
}