import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

public class Main {

    public static void main(String[] args) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(engine);
    }
}
