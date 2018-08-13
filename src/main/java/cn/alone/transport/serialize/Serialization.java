package cn.alone.transport.serialize;

/**
 * Created by rojeralone on 2018-08-13
 */
public interface Serialization {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class clazz);

}
