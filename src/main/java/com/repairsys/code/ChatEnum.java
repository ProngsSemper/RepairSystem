package com.repairsys.code;

/**
 * @Author lyr
 * @create 2019/11/3 1:44
 *
 * 要实现更加强大的在线聊天功能的话，就需要使用类型枚举了
 * 后期完善
 *
 */
public enum  ChatEnum {

    /**
     * 普通的聊天信息
     */
    TALK(200,"聊天消息"),


    /**
     * 对方下线了
     */
    OFFLINE(404,"对方已经下线"),


    /**
     * 新的学生连接，需要给管理员通知，更新聊天列表
     *
     */
    UPDATE_LIST(202,"更新聊天列表"),


    /**
     * 服务器出现未知异常
     *
     */
    ERROR_INFO(500,"出现异常");

    //todo: 待定...



    /**
     * 状态码   code
     */
    private Integer code;
    /**
     * 状态描述 description
     */
    private String desc;

    ChatEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /*
     *
     * 下面是对 属性的 getter 方法
     *
     * */

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
