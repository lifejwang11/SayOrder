package com.wlld.myjecs.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.dromara.easyai.rnnNerveCenter.ModelParameter;

@Getter
@Setter
public class HaveKey {
    private int key;
    private ModelParameter modelParameter;
}
