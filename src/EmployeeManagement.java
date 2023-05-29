import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EmployeeManagement {
    static Scanner sc=new Scanner(System.in);
    static ArrayList<Employee> ar;

    public static void main(String[] args) {
        boolean b=true;

        int empId,exp;
        String empName,jobRole;
        long empPhNo;

        File file=null;
        FileOutputStream fos = null;
        ObjectOutputStream oos=null;
        FileInputStream fis=null;
        ObjectInputStream ois=null;

        try{
            file = new File("/Users/kishore/IdeaProjects/employee.management.system/src/EmpData/empData.txt");
            if(file.exists())
            {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                ar= (ArrayList<Employee>)ois.readObject();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        HashMap<Integer,Employee> empData=new HashMap<>();

        do {
            System.out.println("\n***WELCOME TO EMPLOYEE MANAGEMENT SYSTEM***\n");
            System.out.println("Enter Your Choice:");
            System.out.println("1. View all Employees\n2. Add Employee\n3. Edit Employee Details\n4. Delete Employee Details\n5. Exit");
            int index=sc.nextInt();
            switch (index){
                case 1:{
                    try {
                        fis=new FileInputStream(file);
                        ois=new ObjectInputStream(fis);
                        ar=(ArrayList<Employee>)ois.readObject();
                        display(ar);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println(e);
                    }
                    break;
                }
                case 2:{
                    do {
                        System.out.println("\n----Fill the details of the employee----\nEmployee ID:");
                        empId=sc.nextInt();
                        System.out.println("Employee Name:");
                        empName=sc.next();
                        System.out.println("Employee Ph.No:");
                        empPhNo= sc.nextLong();
                        System.out.println("Employee Designation:");
                        jobRole=sc.next();
                        System.out.println("Experience:");
                        exp=sc.nextInt();
                        empData.put(empId,(new Employee(empId,empName,empPhNo,jobRole,exp)));
                        ar.add(empData.get(empId));
                        display(ar);
                        System.out.println("\n***Details Added Successfully***\n");
                        System.out.println("Add one more Employee?\n1. Yes\n2. No");
                    }while (sc.nextInt()==1);
                    break;
                }
                case 3:{
                    System.out.println("Enter the Employee-ID to Edit the Details");

                }
                case 4:{
                    System.out.println("\nEnter the Employee-ID to Delete the Details:\n");
                    empId=sc.nextInt();
                    for (Map.Entry<Integer, Employee> e:empData.entrySet()) {
                        if(e.getKey()==empId){
                            empData.remove(empId);
                            System.out.println("Details of the Employee is Successfully Removed...");
                        }
                    }
                    System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
                    break;
                }
                case 5:{
                    try {
                        fos = new FileOutputStream(file);
                        oos = new ObjectOutputStream(fos);
                        oos.writeObject(ar);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    finally{
                        try {
                            fis.close();
                            ois.close();
                            fos.close();
                            oos.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    System.out.println("Exiting...!");
                    sc.close();
                    b=false;
                    break;
                }
                default:System.out.println("Please Enter Valid Input!!");
            }
        }while (b);
    }
    static void display(ArrayList<Employee> employee)
    {
        System.out.println("\n--------------Employee List---------------\n");
        System.out.println(String.format("%-10s%-15s%-15s%-20s%-20s", "ID","Name","Phone-No","Designation","Experience"));
        for (Employee e:employee) {
            System.out.println(String.format("%-10s%-15s%-15s%-20s%-20s",e.empId,e.empName,e.empPhNo,e.jobRole,e.exp));
        }
    }
}
