package com.min.demo.flow;

import com.min.demo.flow.util.Log;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyLeaveTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void startProcess() {
        //开启流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("aaa");
        String id = processInstance.getId();
        String businessKey = processInstance.getProcessDefinitionKey();
        String name = processInstance.getProcessDefinitionName();
        Log.d("id=" + id, "businessKey=" + businessKey, "name=" + name);
    }

    @Test
    public void queryProcessInstance() {
        //根据processDefinitionKey查询目前流程实例
        String processDefinitionKey = "aaa";
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByStartTime()
                .asc()
                .list();
        for (ProcessInstance data : processInstanceList) {
            Log.d("processDef=" + data.getProcessDefinitionKey(), "instanceId=" + data.getId()
                    , "activityId=" + data.getActivityId());
        }

//============> [processDef=aaa, instanceId=41c6e718-9e27-11ec-b871-7684ee107ee1, activityId=null]
//============> [processDef=aaa, instanceId=ca7151f9-9e27-11ec-8a0e-7684ee107ee1, activityId=null]
//============> [processDef=aaa, instanceId=08b73ec4-9e28-11ec-b0ce-7684ee107ee1, activityId=null]
    }

    @Test
    public void queryTask() {
        //根据分配人或者流程实例id查询任务
        String assignee = "领导1";
        String processInstanceId = "ca7151f9-9e27-11ec-8a0e-7684ee107ee1";
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
//                .taskAssignee(assignee)
                .list();

        for (Task data : taskList) {
            Log.d("name=" + data.getName()
                    + " id=" + data.getId() + " assignee=" + data.getAssignee());
        }

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        Log.d("process instanceId isEnd=" + processInstance.isEnded());
    }

    @Test
    public void completeTask() {
        //完成任务
        String taskId = "0b3d34a6-9e2d-11ec-a9f9-7684ee107ee1";
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
        for (Task data : taskList) {
            Log.d("complete task : assignee=" + data.getAssignee());
        }
        taskService.complete(taskId);

    }


}
