package Person;

public class Person {
    private int curFloor;
    private int targetFloor;
    private String name;

    public Person(int curFloor, int targetFloor, String name) {
        this.curFloor = curFloor;
        this.targetFloor = targetFloor;
        this.name = name;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        this.curFloor = curFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "curFloor=" + curFloor +
                ", targetFloor=" + targetFloor +
                ", name='" + name + '\'' +
                '}';
    }
}
