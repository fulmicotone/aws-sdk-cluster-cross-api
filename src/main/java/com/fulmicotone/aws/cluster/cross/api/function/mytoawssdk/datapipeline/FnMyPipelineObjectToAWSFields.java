package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.amazonaws.services.datapipeline.model.Field;
import com.fulmicotone.aws.cluster.cross.api.function.my.FnMyPipelineExplodeListField;
import com.fulmicotone.aws.cluster.cross.api.models.Default;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FnMyPipelineObjectToAWSFields implements Function<MyPipelineObject,List<Field>> {

    @Override
    public List<Field> apply(MyPipelineObject po) {

            List<Field> l = Arrays.asList(
                    po.getClass().getFields()).stream()
                    .map(f-> new FnMyPipelineExplodeListField().apply(po, f))
                    .flatMap(List::stream)
                    .map(new FnMyObjectBoxToAWSField())
                    .filter(f -> Optional.ofNullable(f).isPresent())
                    .collect(Collectors.toList());

            if (po instanceof Default == false) {
                l.add(new Field()
                        .withKey("type")
                        .withStringValue(po.getClass()
                                .getSimpleName()));
            }
            return l;
    }
}
