package repository;

import java.util.List;

public interface Thogakade_Modern<T> {
    List<T> getAllData();
    List<String> getListOfPrimaryKeys();


    //Insert Items
    void insertAnItem(T t);

    //Delete An Item
    void deleteAnItem(String primaryID);

    //Update An Item
    void updateAnItem(T t);

}
