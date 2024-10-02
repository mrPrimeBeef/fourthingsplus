package app.entities;

public class Task {
    private int id;
    private String name;
    private boolean done;
    private int userID;

    public Task(int id, String name, boolean done, int userID) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public int getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", done=" + done +
                ", userID=" + userID +
                '}';
    }
}
