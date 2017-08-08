package io.github.biezhi.wechat.handle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.biezhi.wechat.model.GroupMessage;
import io.github.biezhi.wechat.model.UserMessage;

/**
 * 消息处理接口
 */
public interface MessageHandle {

    void wxSync(JsonObject msg);

    void userMessage(UserMessage userMessage);

    void groupMessage(GroupMessage groupMessage);

    void groupMemberChange(String groupId, JsonArray memberList);

    void groupListChange(String groupId, JsonArray memberList);

}
