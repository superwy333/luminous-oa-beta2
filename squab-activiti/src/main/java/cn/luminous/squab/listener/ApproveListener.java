package cn.luminous.squab.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ApproveListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("===========");
        System.out.println(">>>>>>>>ApproveListener<<<<<<<<");
        System.out.println("===========");

    }
}
