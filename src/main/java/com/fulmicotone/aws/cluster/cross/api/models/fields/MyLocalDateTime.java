package com.fulmicotone.aws.cluster.cross.api.models.fields;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MyLocalDateTime extends MyPipeLineObjectField<LocalDateTime> {


    @Override
    public void setVal(LocalDateTime val) {
        super.setVal(val);
    }


    public String getVal(){


        return  Optional.ofNullable(val).isPresent()?val.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")):
                "#{"+Optional.of(placeHolder).get()+"}";


    }
}
