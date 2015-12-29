package solr.model;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public enum  CoreEnum {
    user("core_user"),bs("core_bs");

    private String coreName;

    CoreEnum(String coreName) {
        this.coreName = coreName;
    }
}
