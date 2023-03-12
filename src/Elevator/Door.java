package Elevator;

enum STATE {
    CLOSED,
    OPENED
}
public class Door {
    private STATE state; // state of the door
    private int curFloor; // where the door is currently at

    public Door(int curFloor) {
        this.state = STATE.CLOSED;
        this.curFloor = curFloor;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    @Override
    public String toString() {
        return "Door is " + this.state.toString().toLowerCase() + " at floor #" + this.curFloor;
    }
}
