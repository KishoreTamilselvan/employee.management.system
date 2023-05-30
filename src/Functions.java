import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Functions {
    static void display(HashMap<Integer,Employee> employee){
        System.out.println("\n--------------Employee List---------------\n");
        System.out.println(String.format("%-10s%-15s%-15s%-25s%-20s", "ID","Name","Phone-No","Designation","Exp in Year"));
        for (Map.Entry e:employee.entrySet()) {
            System.out.println(e.getValue());
        }
        autoSave();
    }
    static void autoSave(){
        try {
            EmployeeManagement.fos = new FileOutputStream(EmployeeManagement.file);
            EmployeeManagement.oos = new ObjectOutputStream(EmployeeManagement.fos);
            EmployeeManagement.oos.writeObject(EmployeeManagement.empData);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        finally{
            try {
                EmployeeManagement.fis.close();
                EmployeeManagement.ois.close();
                EmployeeManagement.fos.close();
                EmployeeManagement.oos.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    static boolean checkID(int n){
        for (Employee i:EmployeeManagement.empData.values()){
            if(i.empId==n){
                System.out.println("This ID is already exist. Please enter another ID..!");
                return true;
            }
        }
        return false;
    }
    static boolean checkPhNo(long n){
        if(String.valueOf(n).length()!=10) {
            System.out.println("Please enter the valid 10 digit input..!!");
            return true;
        }else {
            for (Employee i:EmployeeManagement.empData.values()){
                if(i.empPhNo==n){
                    System.out.println("This Phone-No is already exist. Please enter another Number..!");
                    return true;
                }
            }
        }
        return false;
    }
}
