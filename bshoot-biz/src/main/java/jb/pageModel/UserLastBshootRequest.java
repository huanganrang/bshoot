package jb.pageModel;

import java.util.List;

/**
 * Created by zhou on 2016/1/7.
 */
public class UserLastBshootRequest extends HotShootRequest{
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "UserLastBshootRequest{" +
                "userIds=" + userIds +
                '}';
    }
}
