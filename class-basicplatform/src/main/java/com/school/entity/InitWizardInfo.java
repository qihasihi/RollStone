package com.school.entity;

import java.io.Serializable;

/**
 * Created by yuechunyang on 14-3-6.
 */
public class InitWizardInfo implements Serializable {
    private Integer ref;
    private Integer currentStep;
    private Integer success;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
