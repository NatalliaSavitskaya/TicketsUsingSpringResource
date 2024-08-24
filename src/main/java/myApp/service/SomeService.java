package myApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    private final ThisIsMyFirstConditionalBean firstConditionalBean;

    @Autowired
    public SomeService(ThisIsMyFirstConditionalBean firstConditionalBean) {
        this.firstConditionalBean = firstConditionalBean;
    }

    public void execute() {
        firstConditionalBean.performAction();
    }
}
