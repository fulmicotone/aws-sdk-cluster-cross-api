package com.fulmicotone.aws.cluster.cross.api.models.generic;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;

public class MyPipeLineObjectField<T> implements Serializable,IMyObject  {

    protected T val;
    protected String placeHolder;

    public MyPipeLineObjectField() { }


    public MyPipeLineObjectField(T val) {
        this.val = val;
    }

    public void setPlaceHolder(String placeHolder) { this.placeHolder = placeHolder;}

    public void setVal(T val) { this.val = val; }


    public void  ifNotNullRun(Consumer<MyPipeLineObjectField> c){ if(isNull()==false) { c.accept(this);}

    }

    public boolean isNull(){ return
            !Optional.ofNullable(val).isPresent()&&
                    !Optional.ofNullable(placeHolder).isPresent();}

    public String getVal(){
        return  Optional.ofNullable(val).isPresent()?val.toString():
                "#{"+Optional.of(placeHolder).get()+"}";
    }


    public boolean isPlaceHolder(){

        return isNull()==false&&getVal().startsWith("#{");
    }


}
