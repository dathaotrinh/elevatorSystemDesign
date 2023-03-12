package Elevator;

import Person.Person;
import Request.Request;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private int maxFloor; // maximum floor the elevator can go to
    private int minFloor; // minimum floor the elevator can go to
    private Request upRequest; // keeps track of all up requests
    private Request downRequest; // keeps track of all down requests
    private Door door; // keeps track of opening and closing the elevator door
    private Elevator elevator; // keeps track of the elevator

    public ElevatorController(int maxFloor, int minFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
        this.upRequest = new Request();
        this.downRequest = new Request();
        this.door = new Door(minFloor);
        this.elevator = new Elevator(minFloor);
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public Request getUpRequest() {
        return upRequest;
    }

    public void setUpRequest(Request upRequest) {
        this.upRequest = upRequest;
    }

    public Request getDownRequest() {
        return downRequest;
    }

    public void setDownRequest(Request downRequest) {
        this.downRequest = downRequest;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    // process go up request
    public void sendUpRequest(Person person) {
        int curFloor = person.getCurFloor();
        int targetFloor = person.getTargetFloor();

        // check if this action is right (go up)
        // if not, ignore it
        if(targetFloor - curFloor <= 0 || !isValidFloor(curFloor) || !isValidFloor(targetFloor)) {
            return;
        }


        // determine the direction of the elevator
        // based on the first request the elevator receives
        if(this.upRequest.getFloors().isEmpty() && this.downRequest.getFloors().isEmpty()) {
            this.elevator.setDirection(DIRECTION.UP);
        }

        // add this person to the corresponding list
        this.upRequest.getPickUpMap().putIfAbsent(curFloor, new ArrayList<>());
        this.upRequest.getPickUpMap().get(curFloor).add(person);

        this.upRequest.getDropOffMap().putIfAbsent(targetFloor, new ArrayList<>());
        this.upRequest.getDropOffMap().get(targetFloor).add(person);

        // add the current floor and target floor
        // to upRequest
        // this keeps track of when to open the door of the elevator
        this.upRequest.getFloors().add(curFloor);
        this.upRequest.getFloors().add(targetFloor);
    }


    // check if the requested floor is within [minFloor, maxFloor]
    private boolean isValidFloor(int floor) {
        return floor >= this.minFloor && floor <= this.maxFloor;
    }

    // process go down request
    public void sendDownRequest(Person person) {
        // get current floor and target floor
        int curFloor = person.getCurFloor();
        int targetFloor = person.getTargetFloor();

        // check if this action is right (go down)
        // if not, ignore it
        if(targetFloor - curFloor >= 0  || !isValidFloor(curFloor) || !isValidFloor(targetFloor)) {
            return;
        }

        // determine the direction of the elevator
        // based on the first request the elevator receives
        if(this.upRequest.getFloors().isEmpty() && this.downRequest.getFloors().isEmpty()) {
            this.elevator.setDirection(DIRECTION.DOWN);
        }

        // add this person to the corresponding list
        this.downRequest.getPickUpMap().putIfAbsent(curFloor, new ArrayList<>());
        this.downRequest.getPickUpMap().get(curFloor).add(person);

        this.downRequest.getDropOffMap().putIfAbsent(targetFloor, new ArrayList<>());
        this.downRequest.getDropOffMap().get(targetFloor).add(person);

        // add the current floor and target floor
        // to downRequest
        // this keeps track of when to open the door of the elevator
        this.downRequest.getFloors().add(curFloor);
        this.downRequest.getFloors().add(targetFloor);
    }

    // operate the elevator, set the appropriate state (direction) of the elevator
    public void operate() {
        // while the elevator is not idle
        while(this.elevator.getDirection() != DIRECTION.IDLE) {
            // if its going up
            if(this.elevator.getDirection() == DIRECTION.UP) {
                // go up to each requested floor to pick up and drop off people
                while(!upRequest.getFloors().isEmpty()) {
                    // get the target floor
                    int floor = upRequest.getFloors().pollFirst();

                    // check the last up request to see if it is at the same floor as the first down request
                    // if it is, set direction to be waiting
                    if(upRequest.getFloors().isEmpty() && !downRequest.getFloors().isEmpty() && downRequest.getFloors().last() == floor) {
                        downRequest.getFloors().pollLast(); // remove duplicated request
                        this.elevator.setDirection(DIRECTION.WAITING);
                    }
                    printRequest(floor);
                }

                // check if elevator has any down request remaining
                if(!downRequest.getFloors().isEmpty()) {
                    this.elevator.setDirection(DIRECTION.DOWN);
                }

            } else if(this.elevator.getDirection() == DIRECTION.DOWN) { // if elevator's going down
                // go down to each requested floor to pick up and drop off people
                while(!downRequest.getFloors().isEmpty()) {
                    // get the target floor
                    int floor = downRequest.getFloors().pollLast();

                    // check the last down request to see if it is at the same floor as the first up request
                    // if it is, set direction to be waiting
                    if(downRequest.getFloors().isEmpty() && !upRequest.getFloors().isEmpty() && upRequest.getFloors().first() == floor) {
                        upRequest.getFloors().pollFirst(); // remove duplicated request
                        this.elevator.setDirection(DIRECTION.WAITING);
                    }
                    printRequest(floor);
                }
                // check if elevator has any up request remaining
                if(!upRequest.getFloors().isEmpty()) {
                    this.elevator.setDirection(DIRECTION.UP);
                }
            }

            // if there is no more request
            // elevator is set to idle state
            if(upRequest.getFloors().isEmpty() && downRequest.getFloors().isEmpty()) {
                this.elevator.setDirection(DIRECTION.IDLE);
            }
        }

        // clears all requests after each operate()
        this.upRequest.clearAll();
        this.downRequest.clearAll();
    }


    // prints each request at a given floor
    private void printRequest(int destination) {
        // assume that going from one floor to the next floor take 1 second
        // example first floor to second floor takes 1 second
        // first floor to third floor takes 2 seconds
        int countDownTimer = Math.abs(this.elevator.getCurFloor() - destination);

        // use thread to run the timer
        for (int i = 1; i <= countDownTimer; i++) {
            System.out.println(">> Time: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // gets list of people getting picked up and dropped off
        List<Person> dropOffPersonList = null;
        List<Person> pickUpPersonList = null;

        if(this.elevator.getDirection() == DIRECTION.UP) {
            dropOffPersonList = this.upRequest.getDropOffMap().get(destination);
            pickUpPersonList = this.upRequest.getPickUpMap().get(destination);
        } else if(this.elevator.getDirection() == DIRECTION.DOWN) {
            dropOffPersonList = this.downRequest.getDropOffMap().get(destination);
            pickUpPersonList = this.downRequest.getPickUpMap().get(destination);
        } else if(this.elevator.getDirection() == DIRECTION.WAITING) { // tricky situation
            // if the downRequest is empty
            // drop off people going down
            // pick up people going up
            if(this.downRequest.getFloors().isEmpty()) {
                dropOffPersonList = this.downRequest.getDropOffMap().get(destination);
                pickUpPersonList = this.upRequest.getPickUpMap().get(destination);
            } else { // otherwise
                // drop off people going up
                // pick up people going down
                pickUpPersonList = this.downRequest.getPickUpMap().get(destination);
                dropOffPersonList = this.upRequest.getDropOffMap().get(destination);
            }
        }

        // set current floor of the elevator
        this.elevator.setCurFloor(destination);

        // set current floor of each person after dropping off
        this.setCurFloorOfPersonList(dropOffPersonList, destination);

        // set new floor for door
        this.door.setCurFloor(destination);

        // opens door
        this.door.setState(STATE.OPENED);

        // prints door
        System.out.println(this.door.toString());

        // print pick up and drop off list
        this.printPersonList(dropOffPersonList, pickUpPersonList);

        // closes door
        this.door.setState(STATE.CLOSED);

        // prints door
        System.out.println(this.door.toString());
    }

    // print list of people getting dropped off and picked up
    private void printPersonList(List<Person> dropOffPersonList, List<Person> pickUpPersonList) {
        StringBuilder dropOffStr = new StringBuilder();
        dropOffStr.append("Drop off: ");
        if(dropOffPersonList != null) {
            for (Person person :
                    dropOffPersonList) {
                dropOffStr.append(person.getName()).append(" ");
            }
        } else {
            dropOffStr.append("none");
        }

        StringBuilder pickUpStr = new StringBuilder();
        pickUpStr.append("Pick up: ");
        if(pickUpPersonList != null) {
            for (Person person :
                    pickUpPersonList) {
                pickUpStr.append(person.getName()).append(" ");
            }
        } else {
            pickUpStr.append("none");
        }

        System.out.println(pickUpStr);
        System.out.println(dropOffStr);
    }

    // sets current floor of each person after dropping them off
    private void setCurFloorOfPersonList(List<Person> personList, int destination) {
        if(personList != null) {
            for (Person person :
                    personList) {
                person.setCurFloor(destination);
            }
        }
    }

}
