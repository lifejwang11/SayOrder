package com.wlld.myjecs.entity;

import lombok.Getter;
import lombok.Setter;
import org.wlld.naturalLanguage.languageCreator.KeyWordModel;

@Setter
@Getter
public class KeyWordModelMapping {//关键词模型映射
    private int key;
    private KeyWordModel keyWordModel;
}
