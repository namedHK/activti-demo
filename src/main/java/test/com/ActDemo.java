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
     * 工作流引擎
     */
     public static ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
    /**
     * 存储服务
     */
    public static RepositoryService repositoryService = engine.getRepositoryService();

    /**
     * 运行服务
     */
    public static RuntimeService runService = engine.getRuntimeService();

    /**
     * 任务服务
     */
    public static TaskService taskService = engine.getTaskService();

    public static HistoryService historyService = engine.getHistoryService();

    public static void main(String[] args) {

        prosses();

    }

    private static void prosses() {
        //开始部署服务
        repositoryService.createDeployment().addClasspathResource("leave.bpmn20.xml").deploy();

        Map<String,Object> map = new HashMap();
        map.put("user","zhangsansan");
        //启动一个流程实例
        ProcessInstance pi = runService.startProcessInstanceByKey("process",map);

        System.out.println(pi.getProcessDefinitionName());

        //员工请假
        Task task =  taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        String taskId = task.getId();
        System.out.println("当前任务:"+task.getName()+",当前责任人"+task.getAssignee());
        //设置下一个审批人
        map.put("boss","miaojingyi");
        Map<String,Object> taskMap = new HashMap();
        taskMap.put("resion","不想上班");
        taskService.setVariablesLocal(taskId,taskMap);
        taskService.complete(task.getId(),map);




        //查询历史流程相关任务
        serachTaskByProcess(pi.getProcessInstanceId());

        //经理审批
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("当前任务"+task.getName()+",当前责任人"+task.getAssignee());

        taskService.complete(task.getId());


        //任务结束
        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        System.out.println("当前任务节点"+task);




        //查询历史完成任务
        //serachHisTask(taskId);


    }

    private static void serachTaskByProcess(String processInstanceId) {
        List<HistoricTaskInstance> processInstance = historyService.createHistoricTaskInstanceQuery().unfinished().processInstanceId(processInstanceId).list();
        System.out.println("查询进程所有先关流程：");
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
