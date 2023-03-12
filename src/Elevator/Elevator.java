package Elevator;


enum DIRECTION {
    UP, // going up
    DOWN, // going down
    // waiting - tricky situation, only happen when
    // 1. the last floor to drop off people going up is the first floor to pick up people going down
    // 2. the last floor to drop off people going down is the first floor to pick up people going up
    // because there are 4 maps to record who is going up/down, getting on/off the elevator
    // using waiting to make sure that the door of the elevator doesn't open twice at the same floor
    WAITING,

    IDLE // no request received
}
public class Elevator {
    private int curFloor; // current floor where the elevator is at
    private DIRECTION direction; // current direction of the elevator, depends on the first request received

    public Elevator(int curFloor) {
        this.curFloor = curFloor;
        this.direction = DIRECTION.IDLE;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    // prints current floor of the elevator
    public String printCurrentFloor() {
        return "Elevator is currently at: floor #" + this.curFloor;
    }

}
