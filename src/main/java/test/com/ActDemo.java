package com;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActDemo {

    /**
     * ����������
     */
     public static ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
    /**
     * �洢����
     */
    public static RepositoryService repositoryService = engine.getRepositoryService();

    /**
     * ���з���
     */
    public static RuntimeService runService = engine.getRuntimeService();

    /**
     * �������
     */
    public static TaskService taskService = engine.getTaskService();

    public static HistoryService historyService = engine.getHistoryService();

    public static void main(String[] args) {

        prosses();

    }

    private static void prosses() {
        //��ʼ�������
        repositoryService.createDeployment().addClasspathResource("leave.bpmn20.xml").deploy();

        Map<String,Object> map = new HashMap();
        map.put("user","zhangsansan");
        //����һ������ʵ��
        ProcessInstance pi = runService.startProcessInstanceByKey("process",map);

        System.out.println(pi.getProcessDefinitionName());

        //Ա�����
        Task task =  taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        String taskId = task.getId();
        System.out.println("��ǰ����:"+task.getName()+",��ǰ������"+task.getAssignee());
        //������һ��������
        map.put("boss","miaojingyi");
        Map<String,Object> taskMap = new HashMap();
        taskMap.put("resion","�����ϰ�");
        taskService.setVariablesLocal(taskId,taskMap);
        taskService.complete(task.getId(),map);




        //��ѯ��ʷ�����������
        serachTaskByProcess(pi.getProcessInstanceId());

        //��������
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("��ǰ����"+task.getName()+",��ǰ������"+task.getAssignee());

        taskService.complete(task.getId());


        //�������
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("��ǰ����ڵ�"+task);




        //��ѯ��ʷ�������
        //serachHisTask(taskId);


    }

    private static void serachTaskByProcess(String processInstanceId) {
        List<HistoricTaskInstance> processInstance = historyService.createHistoricTaskInstanceQuery().unfinished().processInstanceId(processInstanceId).list();
        System.out.println("��ѯ���������ȹ����̣�");
        for(HistoricTaskInstance his:processInstance){
            System.out.println(his.getName());
        }
    }

    private static void serachHisTask(String taskId) {
        HistoricTaskInstance instance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).includeTaskLocalVariables().singleResult();


        Map<String,Object> dataMap = instance.getTaskLocalVariables();
        for(String key:dataMap.keySet()){
            System.out.println(key+":"+dataMap.get(key));
        }
    }


}
