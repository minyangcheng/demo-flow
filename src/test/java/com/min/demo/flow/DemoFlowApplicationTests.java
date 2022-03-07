package com.min.demo.flow;

import com.min.demo.flow.util.Log;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoFlowApplicationTests {
    @Autowired
    private RepositoryService repositoryService;

    @Test
    void contextLoads() {
    }

    @Test
    void getProcessDefinitions() {
        List<ProcessDefinition> processList = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition data : processList) {
            String id = data.getId();
            String key = data.getKey();
            String name = data.getName();
            Log.d("id=" + id, "key=" + key, "name=" + name);
        }
    }

}
