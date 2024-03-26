create table test.sentence_config
(
    id                       int auto_increment
        primary key,
    type_nub                 int        null comment '语义分类种类数与表my_tree数据条数对应',
    word_vector_dimension    int        null comment '语义分类词嵌入维度，该数字越大，支持的分类复杂度越高，支持数据量越多，越接近大模型，生成问答模型越稳定，但速度越慢',
    qa_word_Vector_dimension int        null comment '问答模型词嵌入维度，该数字越大速度越慢，越能支持更复杂的问答',
    max_word_length          int        null comment '用户输入语句最大长度',
    max_answer_length        int        null comment 'Ai最大回答长度',
    key_word_nerve_deep      int        null comment '/关键词敏感嗅探颗粒度大小',
    times                    int        null comment '训练次数',
    show_log                 tinyint(1) null comment '是否显示训练日志（1显示，0显示）',
    param                    double     null,
    min_length               int        null,
    trust_power_th           double     null,
    sentence_trust_power_th  double     null,
    we_study_point           double     null,
    we_lparam                double     null,
    status                   char       null comment '状态（1 使用和0未使用）',
    created_time             datetime   null comment '创建时间',
    updated_time             datetime   null comment '更新时间'
)
    comment '训练配置表';

insert into sentence_config(title,remark,type_nub, word_vector_dimension, qa_word_Vector_dimension,
                            max_word_length, max_answer_length, key_word_nerve_deep,
                            times, show_log, param, min_length, trust_power_th,
                            sentence_trust_power_th, we_study_point, we_lparam,status,created_time,updated_time) value
    ('默认模型','默认模型',11,21,66,20,20,3,10,1,0.4,5,0.5,0.2,0.01,0.001,'1',now(),null);