package com.fulmicotone.aws.cluster.cross.api.utils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public  class FnNewReflectField implements Function<FnNewReflectField.Param, Field> {


    @Override
    public Field apply(Param param) {

        Field field=null;
        try {

            Constructor<?> c = Field.class.
                    getDeclaredConstructors()[0];
            c.setAccessible(true);

            field = (Field) c.newInstance(param.declaringClass, param.name, param.type, param.modifiers, param.slot, param.signature, param.annotations);


        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return field;
    }

    public  static class Param

    {


        private Param(Class<?> declaringClass, String name, Class<?> type, int modifiers, int slot, String signature, byte[] annotations) {
            this.declaringClass = declaringClass;
            this.name = name;
            this.type = type;
            this.modifiers = modifiers;
            this.slot = slot;
            this.signature = signature;
            this.annotations = annotations;
        }

        private  Class<?> declaringClass;
        private  String name;
        private  Class<?> type;
        private  int modifiers;
        private int slot;
        private  String signature;
        private byte[] annotations;

        public Field apply(){  return  new FnNewReflectField().apply(this);}


        public  static class Builder{

            private Class<?> declaringClass;
            private String name;
            private Class<?> type;
            private int modifiers=1;
            private int slot=0;
            private String signature;
            private byte[] annotation;

            private Builder(){}

            public static Builder newOne(){ return new Builder(); }

            public Builder withDeclaringClass(  Class<?> declaringClass){this.declaringClass=declaringClass; return this;}

            public Builder withName(String name){ this.name=name; return this;}

            public Builder withType(Class<?> type){ this.type=type; return this;}

            public Builder withModifiers(int modifiers){ this.modifiers=modifiers; return this;}

            public Builder withSlot(int slot){ this.slot=slot; return this;}

            public Builder withSignature(String signature){ this.signature=signature;  return this;}

            public Builder withAnnotation(byte[] annotation){ this.annotation=annotation;  return this;}


            public Param build(){

                 return new Param(
                         declaringClass,
                         name,
                         type,
                         modifiers,
                         slot,
                         signature,
                         annotation);
            }




        }



    }
}
