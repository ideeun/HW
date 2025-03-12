import java.util.*;

class ListMerger {
    public static <T> List<T> mergeAlternately(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>();
        int size1 = list1.size(), size2 = list2.size();
        int i = 0, j = 0;
        while (i < size1 || j < size2) {
            if (i < size1) {
                result.add(list1.get(i++));
            }
            if (j < size2) {
                result.add(list2.get(j++));
            }
        }
        return result;
    }
}

class MapPrinter {
    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}



class Task implements Comparable<Task> {
    private final String taskName;
    private final int priority;
    private final int duration;

    public Task(String taskName, int priority, int duration) {
        this.taskName = taskName;
        this.priority = priority;
        this.duration = duration;
    }

    public String getTaskName() { return taskName; }
    public int getPriority() { return priority; }
    public int getDuration() { return duration; }

    @Override
    public int compareTo(Task other) {
        if (this.priority != other.priority)
            return Integer.compare(other.priority, this.priority);
        return Integer.compare(this.duration, other.duration);
    }

    @Override
    public String toString() {
        return "[Priority " + priority + "] " + taskName + " (Duration: " + duration + " mins)";
    }
}

class TaskScheduler {
    private final List<Task> taskList = new ArrayList<>();
    private final List<Task> pendingTasks = new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
        sortTasks();
        System.out.println("Added: " + task);
    }

    private void sortTasks() {
        taskList.sort((t1, t2) -> {
            if (t1.getPriority() != t2.getPriority()) {
                return Integer.compare(t2.getPriority(), t1.getPriority());
            }
            return Integer.compare(t1.getDuration(), t2.getDuration());
        });
    }

    public void processTask() {
        if (!taskList.isEmpty()) {
            Task task = taskList.removeFirst();
            System.out.println("Processing Task: " + task);
        } else if (!pendingTasks.isEmpty()) {
            Task task = pendingTasks.removeFirst();
            System.out.println("Processing Task: " + task);
        } else {
            System.out.println("No tasks to process.");
        }
    }

    public void delayTask(String taskName) {
        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTaskName().equals(taskName)) {
                pendingTasks.add(task);
                iterator.remove();
                System.out.println("Delaying Task: " + taskName);
                return;
            }
        }
        System.out.println("Task not found: " + taskName);
    }

    public void showTasks() {
        System.out.println("Scheduled Tasks:");
        for (Task task : taskList) {
            System.out.println(task);
        }
        System.out.println("Pending Tasks:");
        for (Task task : pendingTasks) {
            System.out.println(task);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 3, 5);
        List<Integer> list2 = Arrays.asList(2, 4, 6, 7);
        System.out.println("Merged List: " + ListMerger.mergeAlternately(list1, list2));


        Map<String, Integer> sampleMap = new HashMap<>();
        sampleMap.put("Alice", 25);
        sampleMap.put("Bob", 30);
        sampleMap.put("Charlie", 35);
        MapPrinter.printMap(sampleMap);


        TaskScheduler scheduler = new TaskScheduler();
        scheduler.addTask(new Task("Code Review", 3, 20));
        scheduler.addTask(new Task("System Update", 5, 45));
        scheduler.addTask(new Task("Database Backup", 2, 30));
        scheduler.addTask(new Task("Deploy New Feature", 5, 50));
        scheduler.addTask(new Task("Bug Fixing", 4, 25));

        scheduler.showTasks();
        scheduler.processTask();
        scheduler.delayTask("Code Review");
        scheduler.showTasks();
        scheduler.processTask();
        scheduler.delayTask("Database Backup");
        scheduler.showTasks();
        scheduler.processTask();
        scheduler.processTask();
        scheduler.processTask();
        scheduler.processTask();
    }
}
