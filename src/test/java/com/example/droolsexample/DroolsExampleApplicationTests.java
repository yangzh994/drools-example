package com.example.droolsexample;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class DroolsExampleApplicationTests {

    private  String TEMPLATE_NO="package com.example.droolsexample\n" +
            "import com.example.droolsexample.Student;\n" +
            "rule \"send hhh message open control command\"\n" +
            "    when\n" +
            "        pp:Student(age > 18)\n" +
            "    then\n" +
            "        pp.show();\n" +
            "end";

    @Test
    void contextLoads() {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        KieFileSystem fileSystem = kieFileSystem.write("src/main/resources/rules/" + UUID.randomUUID() + ".drl", TEMPLATE_NO);

        Results results = kieServices.newKieBuilder(fileSystem).buildAll().getResults();

        if(results.hasMessages(Message.Level.ERROR)){
            System.out.println("error");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

        KieBase kieBase = kieContainer.getKieBase();

        KieSession kieSession = kieBase.newKieSession();

        Student student = new Student();
        student.setAge(10);
        student.setName("yangzh");

        kieSession.insert(student);

        int i = kieSession.fireAllRules();

        System.out.println(i);
    }

}
