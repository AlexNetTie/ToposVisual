package Lost.Architect.Development.Data;

import Lost.Architect.Development.Annotation.Invariant;

import java.util.ArrayList;
import java.util.List;

@Invariant("Data класс")
public class DataObject {

    private final List<Double> list;

    public DataObject(List<Double> list){
        this.list = list;
    }

    public List<Double> copyData(){
        return new ArrayList<>(this.list);
    }
}