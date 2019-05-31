package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline;


import com.fulmicotone.aws.cluster.cross.api.exception.PipelineObjectInstantiationExeception;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Aws pipeline object factory
 * this constrains to initialize the required resource ID
 */
public class MyPipelineObjectFactory {

   private static Logger logger =LoggerFactory.getLogger(MyPipelineObjectFactory.class);



    public static <E extends MyPipelineObject> E newObject(Class<E> clazz, MyPipelineObjectTag tag) {
        try {
            Constructor<?> c = clazz.getDeclaredConstructors()[0];
            c.setAccessible(true);
            E x= (E) c.newInstance();
            x.id = tag.id;
            x.name = tag.name;
            return x;
        } catch (InstantiationException e) {

            throw new PipelineObjectInstantiationExeception(
                    "pipeline Object instantiation Exception:" + clazz.getName()
                    , e.getCause());

        } catch (IllegalAccessException e) {
            throw new PipelineObjectInstantiationExeception(
                    "pipeline Object instantiation Exception:" + clazz.getName()
                    , e.getCause());
        } catch (InvocationTargetException e) {
            throw new PipelineObjectInstantiationExeception(
                    "pipeline Object instantiation Exception:" + clazz.getName()
                    , e.getCause());
        }

    }

    public static <E extends MyPipelineObject> E newObject(PipelineObjectsClasses pipelineObjectClazz,
                                                           String id) {
        return MyPipelineObjectFactory.newObject(pipelineObjectClazz, id,id);

    }

    public static <E extends MyPipelineObject> E newObject(PipelineObjectsClasses pipelineObjectClazz,
                                                           String id, String name) {
        Objects.requireNonNull(id,name);
        MyPipelineObjectTag tag=new MyPipelineObjectTag();
        tag.id.setVal(id);
        tag.name.setVal(name);
        return  (E) MyPipelineObjectFactory.newObject(pipelineObjectClazz.getVal(), tag);
    }

}
