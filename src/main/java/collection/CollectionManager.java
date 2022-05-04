package collection;
import ioManager.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class CollectionManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private TreeSet<City> cityCollection;
    private Date initializationDate;
    private Integer idOrder;
    public CollectionManager(){
        cityCollection = new TreeSet<City>(new CustomComp());
        initializationDate = new Date();
        idOrder = 1;
    }
    public void add(IWritable out, City o) {
        if (!cityCollection.contains(o)) {
            o.setId(idOrder++);
        cityCollection.add(o);
        out.writeln("Объект добавлен");
        }
        else
            out.writeln("Объект уже существует");
    }
    public void addIfMax(IWritable out, City o){
        if (o.compareTo(cityCollection.stream().max((s1,s2)->s1.compareTo(s2)).get())>0){
            add(out,o);
        }
        else
            out.writeln("Объект не был добавлен");
    }
    public void addIfMin(IWritable out, City o){
        if (o.compareTo(cityCollection.stream().min((s1,s2)->s1.compareTo(s2)).get())<0){
            add(out, o);
        }
        else out.writeln("Объект не был добавлен");
    }
    public void clear(IWritable out){
        cityCollection.clear();
        out.writeln("Коллекция очищена");
    }

    public void info(IWritable out) {
        out.writeln("Type: " + cityCollection.getClass().toString());
        out.writeln("Date of initialization: " + initializationDate.toString());
        out.writeln("Amount of elements: " + cityCollection.size());
    }
    public void printAscending(IWritable out){
        cityCollection.forEach((s)-> out.writeln(s.toString()));
    }
    public void printDescending(IWritable out){
        cityCollection.descendingSet().forEach((s)-> out.writeln(s.toString()));
    }
    public void removeAllByTimezone(IWritable out,int timezone){
        cityCollection.removeIf(city -> city.getTimezone()==timezone);
        out.writeln("Объекты удалены");
    }
    public void removeById(IWritable out, int id){
        cityCollection.removeIf(city -> city.getId() == id);
        out.writeln("Объект удален");
    }
    public void removeGreater(IWritable out, City o){
        cityCollection.removeIf((s)->o.compareTo(s)>0);
        out.writeln("Объекты удалены");
    }
    public void save(IWritable out, String path){
        try {
            IWritable fileWriter = new WriterFile(path);
            fileWriter.write(JsonConvertor.toJson(this));
            out.writeln("Коллекция сохранена");
        } catch (IOException e) {
            out.writeln("Ошибка сохранения");
        }

    }
    public void show(IWritable out){
        cityCollection.forEach((s)->out.writeln(s.toString()));
    }
    public void updateById(IWritable out, int id, City o){

        if (cityCollection.stream().anyMatch((s)->s.getId()==id)){
            o.setId(id);
            cityCollection.removeIf((s)->s.getId()==id);
            cityCollection.add(o);
            out.writeln("Объект был изменен");
            return;
        }
        out.writeln("Объект не найден");
    }
    public void load(IWritable out, String path){
        IReadable fileReader;
        try {
            fileReader = new ReaderFile(path);
        } catch (IOException e) {
            out.writeln("Файл коллекции не найден или недоступен");
            return;
        }
        StringBuilder collectionJson = new StringBuilder();
        String s;
        while ((s = fileReader.readline())!=null)
            collectionJson.append(s);
        CollectionManager cm = JsonConvertor.fromJson(collectionJson.toString());
        if (cm==null){
            out.writeln("Файл поврежден");
            cityCollection = new TreeSet<City>(new CustomComp());
            out.writeln("Cоздана пустая коллекция");
            initializationDate = new Date();
            idOrder = 1;
            return;
        }
        this.cityCollection = cm.cityCollection;
        this.initializationDate = cm.initializationDate;
        this.idOrder = cm.idOrder;
        out.writeln("Коллекция была загружена из файла "+path);
    }
}
