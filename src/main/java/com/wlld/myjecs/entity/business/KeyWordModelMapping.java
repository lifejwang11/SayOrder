package com.wlld.myjecs.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.dromara.easyai.naturalLanguage.languageCreator.KeyWordModel;

@Setter
@Getter
public class KeyWordModelMapping {//关键词模型映射
    private int key;
    private KeyWordModel keyWordModel;
}
