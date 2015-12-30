package solr.model;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public enum  CoreEnum {
    user("core_user",UserEntity.class),bs("core_bs",BsEntity.class);

    private String coreName;
    private Class clazz;

    CoreEnum(String coreName, Class clazz) {
        this.coreName = coreName;
        this.clazz = clazz;
    }

    public String getCoreName(){
        return coreName;
    }

    public Class getClazz(){
        return clazz;
    }
}
