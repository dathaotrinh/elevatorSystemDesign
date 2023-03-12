package Elevator;

import Person.Person;

public class ElevatorTest {
    public static void main(String[] args) {
        ElevatorController elevatorController = new ElevatorController(10, 1);

        // scenario 1: elevator receives 1 up request, pick up the person at the same floor
        System.out.println("Scenario 1: elevator receives 1 up request, pick up the person at the same floor");
        Person person1 = new Person(1,5, "Person 1");
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendUpRequest(person1);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 2: elevator receives 1 up request, pick up the person at a different floor
        System.out.println("Scenario 2: elevator receives 1 up request, pick up the person at a different floor");
        Person person2 = new Person(2,9, "Person 2");
        elevatorController.getElevator().setCurFloor(1); // set curFloor of the elevator to be 1
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendUpRequest(person2);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 3: elevator receives 1 down request, pick up the person at the same floor
        System.out.println("Scenario 3: elevator receives 1 down request, pick up the person at the same floor");
        Person person3 = new Person(4,3, "Person 3");
        elevatorController.getElevator().setCurFloor(4); // set curFloor of the elevator to be 4
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendDownRequest(person3);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 4: elevator receives 1 down request, pick up the person at a different floor
        System.out.println("Scenario 4: elevator receives 1 down request, pick up the person at a different floor");
        Person person4 = new Person(7,3, "Person 4");
        elevatorController.getElevator().setCurFloor(9); // set curFloor of the elevator to be 9
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendDownRequest(person4);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 5: elevator receives up request when the person wants to go down => ignore
        System.out.println("Scenario 5: elevator receives up request when the person wants to go down => ignore");
        Person person5 = new Person(7,3, "Person 5");
        elevatorController.getElevator().setCurFloor(9); // set curFloor of the elevator to be 9
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendUpRequest(person5);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 6: elevator receives down request when the person wants to go up => ignore
        System.out.println("Scenario 6: elevator receives down request when the person wants to go up => ignore");
        Person person6 = new Person(2,4, "Person 5");
        elevatorController.getElevator().setCurFloor(1); // set curFloor of the elevator to be 1
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendDownRequest(person6);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 7: elevator receives a mix of requests
        System.out.println("Scenario 7: elevator receives a mix of requests");
        Person person7 = new Person(2,4, "Person 7");
        Person person8 = new Person(3,7, "Person 8");
        Person person9 = new Person(9,7, "Person 9");
        Person person10 = new Person(2,4, "Person 10");
        elevatorController.getElevator().setCurFloor(1); // set curFloor of the elevator to be 1
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendUpRequest(person7);
        elevatorController.sendUpRequest(person8);
        elevatorController.sendDownRequest(person9);
        elevatorController.sendUpRequest(person10);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 8 (tricky): continue the scenario 7, elevator receives a mix of requests
        // (not setting the cur floor of the elevator beforehand)
        // the last floor to drop off people going up is the first floor to pick up people going down
        System.out.println("Scenario 8 (tricky): the last floor to drop off people going up is the first floor to pick up people going down");
        Person person11 = new Person(4,6, "Person 11");
        Person person12 = new Person(4,7, "Person 12");
        Person person13 = new Person(6,7, "Person 13");
        Person person14 = new Person(7,2, "Person 14");
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendUpRequest(person11);
        elevatorController.sendUpRequest(person12);
        elevatorController.sendUpRequest(person13);
        elevatorController.sendDownRequest(person14);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();

        // scenario 9 (tricky): elevator receives a mix of requests
        // the last floor to drop off people going down is the first floor to pick up people going up
        System.out.println("scenario 9 (tricky): the last floor to drop off people going down is the first floor to pick up people going up");
        Person person15 = new Person(6,3, "Person 15");
        Person person16 = new Person(3,7, "Person 16");
        Person person17 = new Person(2,7, "Person 17");
        Person person18 = new Person(7,2, "Person 18");
        elevatorController.getElevator().setCurFloor(1); // set curFloor of the elevator to be 1
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        elevatorController.sendDownRequest(person15);
        elevatorController.sendUpRequest(person16);
        elevatorController.sendUpRequest(person17);
        elevatorController.sendDownRequest(person18);
        elevatorController.operate();
        System.out.println(elevatorController.getElevator().printCurrentFloor());
        System.out.println();
    }
}