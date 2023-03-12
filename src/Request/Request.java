package Request;

import Person.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


public class Request {
    private TreeSet<Integer> floors; // all floors, no duplicated, ascending order
    private Map<Integer, List<Person>> pickUpMap; // (floor - personList) map when picking up people
    private Map<Integer, List<Person>> dropOffMap; // (floor - personList) map when dropping off people

    public Request() {
        floors = new TreeSet<>();
        pickUpMap = new HashMap<>();
        dropOffMap = new HashMap<>();
    }

    public TreeSet<Integer> getFloors() {
        return floors;
    }

    public void setFloors(TreeSet<Integer> floors) {
        this.floors = floors;
    }

    public Map<Integer, List<Person>> getPickUpMap() {
        return pickUpMap;
    }

    public void setPickUpMap(Map<Integer, List<Person>> pickUpMap) {
        this.pickUpMap = pickUpMap;
    }

    public Map<Integer, List<Person>> getDropOffMap() {
        return dropOffMap;
    }

    public void setDropOffMap(Map<Integer, List<Person>> dropOffMap) {
        this.dropOffMap = dropOffMap;
    }

    // clears all requests
    public void clearAll() {
        this.floors.clear();
        this.pickUpMap.clear();
        this.dropOffMap.clear();
    }
}
